package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controllers.Constants;
import dao.DBConnect;

public class UserDetailModel {

	private DBConnect dbConn;
	private Statement stmt = null;
	private ResultSet rs = null;

	public UserDetailModel() throws SQLException {
		dbConn = new DBConnect();
	}

	// Create table for storing User Details
	public void createUserDetailsDBTable() {
		try {
			System.out.println("Connecting to database to create UserDetails db table...");
			// Establishing connection
			System.out.println("Connected to database successfully!");

			System.out.println("Creating UserDetails db table in given database...");
			// Execute create query
			stmt = dbConn.connect().createStatement();
			String sql = "CREATE TABLE " + Constants.getUserDetails() + "(user_id INTEGER NOT NULL AUTO_INCREMENT, "
					+ " username VARCHAR(20), " + " email VARCHAR(30), " + " address VARCHAR(50), "
					+ " contact VARCHAR(10), " + " password VARCHAR(100), " + " PRIMARY KEY(user_id))";
			stmt.executeUpdate(sql);
			System.out.println("Created UserDetails db table in given database!");
			// Close db connection
			dbConn.connect().close();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to insert user details into the database
	public boolean insertUserDetailRecords(String username, String email, String address, String contact,
			String password) {
		boolean flag = false;
		try {
			stmt = dbConn.connect().createStatement();
			String sql = null;

			sql = "INSERT INTO " + Constants.getUserDetails() + "(username,email,address,contact,password) "
					+ "VALUES ('" + username + "', '" + email + "', '" + address + "', '" + contact + "', '" + password
					+ "' )";

			stmt.executeUpdate(sql);

			System.out.println("Records Inserted into UserDetailsDB.");
			flag = true;
			dbConn.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		System.out.println("flag value after insert: " + flag);
		return flag;
	}

	// Method to search for user details in the database
	public boolean searchUserDetailsDB(String username, String password) {
		boolean flag = false;
		try {
			stmt = dbConn.connect().createStatement();
			String sql = null;
			sql = "SELECT username, password FROM " + Constants.getUserDetails() + " WHERE username = '" + username
					+ "' AND password = '" + password + "' ";
			rs = stmt.executeQuery(sql);
			System.out.println("sql: " + sql);
			System.out.println("rs: " + rs);
			if (rs.next())
				flag = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Method to get user ID from the database
	public int getIdFromUserDetailsDB(String username, String password) {
		boolean flag = false;
		int userId = 0;
		try {
			stmt = dbConn.connect().createStatement();
			String sql = null;
			sql = "SELECT user_id FROM " + Constants.getUserDetails() + " WHERE username = '" + username
					+ "' AND password = '" + password + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;
				userId = rs.getInt(1);
				System.out.println("userId: " + userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userId;
	}
}
