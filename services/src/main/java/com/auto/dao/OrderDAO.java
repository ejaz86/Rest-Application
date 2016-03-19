package com.auto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.auto.common.DBConnector;
import com.auto.dto.OrderDTO;
import com.auto.exceptions.DataAccessSqlException;

public class OrderDAO {
	private static Log LOGGER = LogFactory.getLog(OrderDAO.class);
	private static final String CREATE_ORDER = "insert into orders (gid,pid,description,order_status,invoice,pdid,uid) values (?,?,?,?,?,?,?)";

	public int placeOrder(OrderDTO orderDTO) throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			pstmt = conn.prepareStatement(CREATE_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, orderDTO.getVid());
			pstmt.setLong(2, orderDTO.getPid());
			pstmt.setString(3, orderDTO.getDescription());
			pstmt.setString(4, orderDTO.getOrderStatus());
			pstmt.setDouble(5, orderDTO.getInvoice());
			pstmt.setLong(6, orderDTO.getDid());
			pstmt.setLong(7, orderDTO.getUid());
			LOGGER.info("create order query : " + pstmt.toString());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			LOGGER.error("error in create order", e);
			throw new DataAccessSqlException("error in create order");
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return id;
	}

}
