package com.auto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.auto.common.DBConnector;
import com.auto.dto.PartsDetailDTO;
import com.auto.exceptions.DataAccessSqlException;

public class PartsDAO {

	private static Log LOGGER = LogFactory.getLog(PartsDAO.class);
	private static final String CREATE_PARTS = "insert into parts (pname,description,image,quantity,price) values(?,?,?,?,?)";
	private static final String UPDATE_PARTS = "update parts set pname = ? ,description = ? ,image = ? ,quantity = ? ,price = ? where pid = ?";
	private static final String GET_PARTS = "select pid,pname,description,image,quantity,price from parts";
	private static final String PARTS_WHERE = " where pid = ?";
	private static final String DELETE_PARTS = "delete from parts where pid = ?";

	public int createParts(PartsDetailDTO partsDetailDTO)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		int id = 0;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(CREATE_PARTS,
					PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, partsDetailDTO.getpName());
			pstmt.setString(2, partsDetailDTO.getDescription());
			pstmt.setString(3, partsDetailDTO.getImageUrl());
			pstmt.setInt(4, partsDetailDTO.getQuantity());
			pstmt.setDouble(5, partsDetailDTO.getPrice());
			LOGGER.info("create parts query : " + pstmt.toString());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			LOGGER.error("error in create parts", e);
			throw new DataAccessSqlException("error in create parts");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return id;
	}

	public void updateParts(PartsDetailDTO partsDetailDTO)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE_PARTS);
			pstmt.setString(1, partsDetailDTO.getpName());
			pstmt.setString(2, partsDetailDTO.getDescription());
			pstmt.setString(3, partsDetailDTO.getImageUrl());
			pstmt.setInt(4, partsDetailDTO.getQuantity());
			pstmt.setDouble(5, partsDetailDTO.getPrice());
			pstmt.setLong(6, partsDetailDTO.getPid());
			LOGGER.info("update parts query : " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("error in update parts", e);
			throw new DataAccessSqlException("error in update parts");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public List<PartsDetailDTO> getPartsDetails(long pid)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PartsDetailDTO> partsDetailDTOs = new ArrayList<PartsDetailDTO>();
		try {
			if (pid > 0) {
				pstmt = conn.prepareStatement(GET_PARTS + PARTS_WHERE);
				pstmt.setLong(1, pid);
			} else {
				pstmt = conn.prepareStatement(GET_PARTS);
			}
			LOGGER.info("get parts details query : " + pstmt.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PartsDetailDTO dto = new PartsDetailDTO();
				dto.setDescription(rs.getString("description"));
				dto.setImageUrl(rs.getString("image"));
				dto.setPid(rs.getLong("pid"));
				dto.setpName(rs.getString("pname"));
				dto.setPrice(rs.getDouble("price"));
				dto.setQuantity(rs.getInt("quantity"));
				partsDetailDTOs.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("error in get parts details", e);
			throw new DataAccessSqlException("error in get parts details");
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return partsDetailDTOs;
	}

	public void deleteParts(PartsDetailDTO partsDetailDTO)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE_PARTS);
			pstmt.setLong(1, partsDetailDTO.getPid());
			LOGGER.info("delete parts query : " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("error in delete parts", e);
			throw new DataAccessSqlException("error in delete parts");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

}
