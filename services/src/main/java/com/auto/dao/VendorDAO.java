package com.auto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.auto.common.DBConnector;
import com.auto.dto.VendorDetailDTO;
import com.auto.exceptions.DataAccessSqlException;

public class VendorDAO {

	private static Log LOGGER = LogFactory.getLog(VendorDAO.class);
	private static final String CREATE_VENDER = "insert into garage (g_name,contact,contact2,email,address,zone,city,state,lat,"
			+ "lng,rating,description,commision_discuss) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_VENDER = "update garage set g_name = ?,contact = ?,contact2 = ?,email = ? ,"
			+ "address = ?,zone = ? ,city = ?,state = ?,lat = ? ,lng = ?,rating = ? ,description = ?,"
			+ "commision_discuss = ? where gid = ?";
	private static final String GET_VENDER = "select gid,g_name,contact,contact2,email,address,zone,city,state,lat,lng,"
			+ "enrolled_date,rating,description,commision_discuss from garage";
	private static final String VENDER_WHERE = " where gid = ?";
	private static final String DELETE_VENDER = "delete from garage where gid = ?";

	public int createVendor(VendorDetailDTO vendorDetailDTO)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			pstmt = conn.prepareStatement(CREATE_VENDER,
					PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, vendorDetailDTO.getVendorName());
			pstmt.setLong(2, vendorDetailDTO.getContact());
			pstmt.setLong(3, vendorDetailDTO.getContact2());
			pstmt.setString(4, vendorDetailDTO.getEmail());
			pstmt.setString(5, vendorDetailDTO.getAddress());
			pstmt.setInt(6, vendorDetailDTO.getZone());
			pstmt.setInt(7, vendorDetailDTO.getCity());
			pstmt.setInt(8, vendorDetailDTO.getState());
			pstmt.setLong(9, vendorDetailDTO.getLat());
			pstmt.setLong(10, vendorDetailDTO.getLng());
			pstmt.setInt(11, vendorDetailDTO.getRating());
			pstmt.setString(12, vendorDetailDTO.getDescription());
			pstmt.setDouble(13, vendorDetailDTO.getCommission());
			LOGGER.info("create vendor query : " + pstmt.toString());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			LOGGER.error("error in create vendor", e);
			throw new DataAccessSqlException("error in create vendor");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return id;
	}

	public void updateVendor(VendorDetailDTO vendorDetailDTO)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE_VENDER);
			pstmt.setString(1, vendorDetailDTO.getVendorName());
			pstmt.setLong(2, vendorDetailDTO.getContact());
			pstmt.setLong(3, vendorDetailDTO.getContact2());
			pstmt.setString(4, vendorDetailDTO.getEmail());
			pstmt.setString(5, vendorDetailDTO.getAddress());
			pstmt.setInt(6, vendorDetailDTO.getZone());
			pstmt.setInt(7, vendorDetailDTO.getCity());
			pstmt.setInt(8, vendorDetailDTO.getState());
			pstmt.setLong(9, vendorDetailDTO.getLat());
			pstmt.setLong(10, vendorDetailDTO.getLng());
			pstmt.setInt(11, vendorDetailDTO.getRating());
			pstmt.setString(12, vendorDetailDTO.getDescription());
			pstmt.setDouble(13, vendorDetailDTO.getCommission());
			pstmt.setLong(14, vendorDetailDTO.getVid());
			LOGGER.info("update vendor query : " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("error in update vendor", e);
			throw new DataAccessSqlException("error in update vendor");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public List<VendorDetailDTO> getVendorDetails(long pid)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VendorDetailDTO> vendorDetailDTOs = new ArrayList<VendorDetailDTO>();
		try {
			if (pid > 0) {
				pstmt = conn.prepareStatement(GET_VENDER + VENDER_WHERE);
				pstmt.setLong(1, pid);
			} else {
				pstmt = conn.prepareStatement(GET_VENDER);
			}
			LOGGER.info("get vendor details query : " + pstmt.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				VendorDetailDTO dto = new VendorDetailDTO();
				dto.setAddress(rs.getString("address"));
				dto.setCity(rs.getInt("city"));
				dto.setCommission(rs.getDouble("commision_discuss"));
				dto.setContact(rs.getLong("contact"));
				dto.setContact2(rs.getLong("contact2"));
				dto.setDescription(rs.getString("description"));
				dto.setEmail(rs.getString("email"));
				dto.setEnrolledDate(rs.getDate("enrolled_date"));
				dto.setLat(rs.getLong("lat"));
				dto.setLng(rs.getLong("lng"));
				dto.setRating(rs.getInt("rating"));
				dto.setState(rs.getInt("state"));
				dto.setVendorName(rs.getString("g_name"));
				dto.setVid(rs.getLong("gid"));
				dto.setZone(rs.getInt("zone"));
				vendorDetailDTOs.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("error in get vendor details", e);
			throw new DataAccessSqlException("error in get vendor details");
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return vendorDetailDTOs;
	}

	public void deleteVendor(VendorDetailDTO vendorDetailDTO)
			throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE_VENDER);
			pstmt.setLong(1, vendorDetailDTO.getVid());
			LOGGER.info("delete vendor query : " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("error in delete vendor", e);
			throw new DataAccessSqlException("error in delete vendor");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

}
