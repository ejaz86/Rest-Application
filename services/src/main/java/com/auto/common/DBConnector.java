package com.auto.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBConnector {
	private static Log LOGGER = LogFactory.getLog(DBConnector.class);
	private static String userName = "ejaz";
	private static String pass = "ejaz12345";
	private static String connection_string = "jdbc:mysql://autog.cgvyggyminyw.us-west-2.rds.amazonaws.com:3306/autogenie";

	private DBConnector() {

	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.error("error in registering the driver", e);
		}
	}

	@Deprecated
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager
					.getConnection(connection_string, userName, pass);
		} catch (Exception e) {
			LOGGER.error("error in get connection", e);
		}
		return con;
	}

	public static Connection getPooledConnection() {
		Connection conn = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/autogenie");
			conn = ds.getConnection();
		} catch (Exception e) {
			LOGGER.error("error in getPooledConnection : ", e);
		}
		return conn;
	}

	public static void close(Connection... con) {
		try {
			if (con != null) {
				for (Connection connection : con) {
					connection.close();
				}
			}
		} catch (Exception e) {
			LOGGER.error("error in closing connection", e);
		}
	}

	public static void close(ResultSet... rs) {
		try {
			if (rs != null) {
				for (ResultSet resultSet : rs) {
					resultSet.close();
				}
			}
		} catch (Exception e) {
			LOGGER.error("error in closing resultSet", e);
		}
	}

	public static void close(Statement... pstmt) {
		try {
			if (pstmt != null) {
				for (Statement statement : pstmt) {
					statement.close();
				}
			}
		} catch (Exception e) {
			LOGGER.error("error in closing statement", e);
		}
	}
}
