package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	// Define database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";

	// Database credentials
	static final String USER = "fp510", PASS = "510";

	/**
	 * Establishes a connection to the database.
	 * 
	 * @return A Connection object representing the connection to the database.
	 * @throws SQLException if a database access error occurs.
	 */
	public Connection connect() throws SQLException {
		// Establishes a connection using DriverManager and returns the connection
		// object.
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

}
