package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controllers.Constants;
import dao.DBConnect;

public class EventsModel {

	// Declare instance variables
	private DBConnect dbConn;
	private Statement stmt = null;
	private ResultSet rs = null;

	// Constructor
	public EventsModel() throws SQLException {
		// Initialize DBConnect instance
		dbConn = new DBConnect();
	}

	// Method to create events table in the database
	public void createEventsTable() {
		try {
			// Establish database connection and create statement
			dbConn = new DBConnect();
			stmt = dbConn.connect().createStatement();
			// SQL query to create events table if not exists
			String sql = "CREATE TABLE IF NOT EXISTS " + Constants.getEvents() + " ("
					+ "event_id INTEGER NOT NULL AUTO_INCREMENT, " + "event_name VARCHAR(20), " + "price numeric(8,2), "
					+ "Description VARCHAR(50), " + "PRIMARY KEY(event_id))";
			// Execute SQL query
			stmt.executeUpdate(sql);
			// Close database connection
			dbConn.connect().close();
			System.out.println("Events table created successfully.");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to insert event records into the database
	public boolean insertEventRecords(String eventName, Double price, String description) {
		boolean flag = false;
		try {
			// Establish database connection and create statement
			stmt = dbConn.connect().createStatement();
			// SQL query to insert event records
			String sql = "INSERT INTO " + Constants.getEvents() + " (event_name, price, Description) " + "VALUES ('"
					+ eventName + "', '" + price + "', '" + description + "')";
			// Execute SQL query
			stmt.executeUpdate(sql);
			// Set flag to true indicating successful insertion
			flag = true;
			// Close database connection
			dbConn.connect().close();
			System.out.println("Event record inserted successfully.");
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return flag;
	}

	// Method to update event in the database
	public void updateEvent(int event_id, String event_name, String price, String description) {
		try {
			// Establish database connection and create statement
			dbConn = new DBConnect();
			stmt = dbConn.connect().createStatement();
			// SQL query to update event
			String query = "UPDATE " + Constants.getEvents() + " SET event_name = '" + event_name + "', price = '"
					+ price + "', Description = '" + description + "' WHERE event_id = '" + event_id + "'";
			// Execute SQL query
			stmt.executeUpdate(query);
			// Close database connection
			dbConn.connect().close();
			System.out.println("Event updated successfully.");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to delete event from the database
	public void deleteEvent(int id) {
		try {
			// Establish database connection and create statement
			dbConn = new DBConnect();
			stmt = dbConn.connect().createStatement();
			// SQL query to delete event
			String sql = "DELETE FROM " + Constants.getEvents() + " WHERE event_id = '" + id + "'";
			// Execute SQL query
			stmt.executeUpdate(sql);
			// Close database connection
			dbConn.connect().close();
			System.out.println("Event deleted successfully.");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method to fetch event list from the database
	public ResultSet fetchEventListFromDB() throws SQLException {
		// Establish database connection and create statement
		dbConn = new DBConnect();
		stmt = dbConn.connect().createStatement();
		// SQL query to fetch event list
		String query = "SELECT * FROM " + Constants.getEvents();
		// Execute SQL query and get result set
		rs = stmt.executeQuery(query);
		// Close database connection
		dbConn.connect().close();
		System.out.println("Event list fetched successfully.");

		return rs;
	}
}
