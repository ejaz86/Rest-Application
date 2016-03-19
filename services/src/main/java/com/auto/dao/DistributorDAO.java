package com.auto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.auto.common.DBConnector;
import com.auto.dto.DistributorDetailDTO;
import com.auto.exceptions.DataAccessSqlException;

public class DistributorDAO {

	private static Log LOGGER = LogFactory.getLog(DistributorDAO.class);
	private static final String CREATE_DISTRIBUTOR = "insert into distributor (pd_name,contact,contact2,email,address,zone,city,"
			+ "state,lat,lng,rating,description,commission) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_DISTRIBUTOR = "update distributor set pd_name = ? , contact = ? , contact2 = ? , "
			+ "email = ? , address = ? , zone = ? ,city = ? , state = ? , lat = ? , lng = ?  , rating = ? , "
			+ "description = ? , commission = ? where pdid = ?";
	private static final String GET_DISTRIBUTOR = "select 	pdid, pd_name, contact, contact2, email, address, zone, city,state, "
			+ "lat, lng,enrolled_date, rating, description, commission from distributor";
	private static final String DISTRIBUTOR_WHERE = " where pdid = ?";
	private static final String DELETE_DISTRIBUTOR = "delete from distributor where pdid = ?";

	public int createDistributor(DistributorDetailDTO distributorDetailDTO) throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		int id = 0;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(CREATE_DISTRIBUTOR, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, distributorDetailDTO.getdName());
			pstmt.setLong(2, distributorDetailDTO.getContact());
			pstmt.setLong(3, distributorDetailDTO.getContact2());
			pstmt.setString(4, distributorDetailDTO.getEmail());
			pstmt.setString(5, distributorDetailDTO.getAddress());
			pstmt.setInt(6, distributorDetailDTO.getZone());
			pstmt.setInt(7, distributorDetailDTO.getCity());
			pstmt.setInt(8, distributorDetailDTO.getState());
			pstmt.setLong(9, distributorDetailDTO.getLat());
			pstmt.setLong(10, distributorDetailDTO.getLng());
			pstmt.setInt(11, distributorDetailDTO.getRating());
			pstmt.setString(12, distributorDetailDTO.getDescription());
			pstmt.setDouble(13, distributorDetailDTO.getCommission());
			LOGGER.info("create distributor query : " + pstmt.toString());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			LOGGER.error("error in create distributor", e);
			throw new DataAccessSqlException("error in create distributor");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return id;
	}

	public void updateDistributor(DistributorDetailDTO distributorDetailDTO) throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE_DISTRIBUTOR);
			pstmt.setString(1, distributorDetailDTO.getdName());
			pstmt.setLong(2, distributorDetailDTO.getContact());
			pstmt.setLong(3, distributorDetailDTO.getContact2());
			pstmt.setString(4, distributorDetailDTO.getEmail());
			pstmt.setString(5, distributorDetailDTO.getAddress());
			pstmt.setInt(6, distributorDetailDTO.getZone());
			pstmt.setInt(7, distributorDetailDTO.getCity());
			pstmt.setInt(8, distributorDetailDTO.getState());
			pstmt.setLong(9, distributorDetailDTO.getLat());
			pstmt.setLong(10, distributorDetailDTO.getLng());
			pstmt.setInt(11, distributorDetailDTO.getRating());
			pstmt.setString(12, distributorDetailDTO.getDescription());
			pstmt.setDouble(13, distributorDetailDTO.getCommission());
			pstmt.setLong(14, distributorDetailDTO.getDid());
			LOGGER.info("update distributor query : " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("error in update distributor", e);
			throw new DataAccessSqlException("error in update distributor");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public List<DistributorDetailDTO> getDistributorDetails(long did) throws Exception {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DistributorDetailDTO> distributorDetailDTOs = new ArrayList<DistributorDetailDTO>();
		try {
			if (did > 0) {
				pstmt = conn.prepareStatement(GET_DISTRIBUTOR + DISTRIBUTOR_WHERE);
				pstmt.setLong(1, did);
			} else {
				pstmt = conn.prepareStatement(GET_DISTRIBUTOR);
			}
			LOGGER.info("get distributor details query : " + pstmt.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DistributorDetailDTO dto = new DistributorDetailDTO();
				dto.setAddress(rs.getString("address"));
				dto.setCity(rs.getInt("city"));
				dto.setCommission(rs.getDouble("commission"));
				dto.setContact(rs.getLong("contact"));
				dto.setContact2(rs.getLong("contact2"));
				dto.setDescription(rs.getString("description"));
				dto.setDid(rs.getLong("pdid"));
				dto.setdName(rs.getString("pd_name"));
				dto.setEmail(rs.getString("email"));
				dto.setEnrolledDate(rs.getDate("enrolled_date"));
				dto.setLat(rs.getLong("lat"));
				dto.setLng(rs.getLong("lng"));
				dto.setRating(rs.getInt("rating"));
				dto.setState(rs.getInt("state"));
				dto.setZone(rs.getInt("zone"));
				distributorDetailDTOs.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("error in get distributor details", e);
			throw new Exception(e);
			// throw new DataAccessSqlException("error in get distributor
			// details");
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return distributorDetailDTOs;
	}

	public void deleteDistributor(DistributorDetailDTO distributorDetailDTO) throws DataAccessSqlException {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE_DISTRIBUTOR);
			pstmt.setLong(1, distributorDetailDTO.getDid());
			LOGGER.info("delete distributor query : " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("error in delete distributor", e);
			throw new DataAccessSqlException("error in delete distributor");
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}
}
