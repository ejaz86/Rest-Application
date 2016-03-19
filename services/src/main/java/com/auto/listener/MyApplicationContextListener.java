package com.auto.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class MyApplicationContextListener implements ServletContextListener {
	private static final Log LOGGER = LogFactory
			.getLog(MyApplicationContextListener.class);

	public void contextInitialized(ServletContextEvent sce) {
	}

	public void contextDestroyed(ServletContextEvent sce) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver d = null;
		while (drivers.hasMoreElements()) {
			try {
				d = drivers.nextElement();
				DriverManager.deregisterDriver(d);
				LOGGER.warn(String.format("Driver %s deregistered", d));
			} catch (SQLException ex) {
				LOGGER.warn(String.format("Error deregistering driver %s", d),
						ex);
			}
		}
		try {
			AbandonedConnectionCleanupThread.shutdown();
		} catch (InterruptedException e) {
			LOGGER.warn("SEVERE problem in cleaning up: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
