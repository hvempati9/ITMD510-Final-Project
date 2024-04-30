package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controllers.Constants;
import dao.DBConnect;

public class UserEventsModel {

	private DBConnect dbConn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	// Create table for storing Event Details
	public void createUserEventsTable() {
		try {
			// Open a connection
			System.out.println("Connecting to database to create UserEvents DB Table...");
			System.out.println("Connected to database successfully!");

			// Execute create query
			System.out.println("Creating UserEvents table in given database...");
			dbConn = new DBConnect();
			stmt = dbConn.connect().createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS " + Constants.getUserEvents()
					+ "(user_id INTEGER NOT NULL AUTO_INCREMENT, " + " user_name VARCHAR(20), "
					+ " event_name VARCHAR(20), " + " price numeric(8,2), " + " PRIMARY KEY(user_id))";

			stmt.executeUpdate(sql);
			System.out.println("Created UserEvents table in given database!");
			dbConn.connect().close(); // close dbConn connection
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to add user event
	public void addUserEvent(String username, String eventName, Double price) {
		try {
			dbConn = new DBConnect();
			stmt = dbConn.connect().createStatement();

			String sql = "INSERT INTO " + Constants.getUserEvents() + "(user_name , event_name ,price) " + "VALUES (' "
					+ username + " ', ' " + eventName + " ', ' " + price + " ')";
			stmt.execute(sql);
			dbConn.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to update user event
	public void updateUserEvent(int userid, String username, String eventName, String price) {
		try {
			dbConn = new DBConnect();

			stmt = dbConn.connect().createStatement();
			String query = "UPDATE " + Constants.getUserEvents() + " SET user_name = ' " + username
					+ "' , event_name = ' " + eventName + " ' , price = ' " + price + " ' WHERE user_id = ' " + userid
					+ "'";
			stmt.executeUpdate(query);
			dbConn.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to delete user event
	public void deleteEvent(int id) {
		try {
			dbConn = new DBConnect();

			stmt = dbConn.connect().createStatement();
			String sql = "DELETE FROM " + Constants.getUserEvents() + " WHERE user_id = '" + id + "'";
			stmt.executeUpdate(sql);
			dbConn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	// Method to fetch user events
	public ResultSet fetchUserEvents() {
		try {
			dbConn = new DBConnect();
			stmt = dbConn.connect().createStatement();

			String query = "SELECT * FROM " + Constants.getUserEvents();

			rs = stmt.executeQuery(query);
			dbConn.connect().close();

			return rs;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}
}
