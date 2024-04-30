package controllers;

public final class Constants {

	// Defining table names
	private static final String USER_DETAILS = "hvemp_user_details";
	private static final String EVENTS = "hvemp_events";
	private static final String USER_EVENTS = "hvemp_user_events";
	private static final String ADMIN_USERNAME = "root";
	private static final String ADMIN_PASSWORD = "admin";

	/**
	 * Returns the table name for user details.
	 * 
	 * @return the table name for user details
	 */
	public static String getUserDetails() {
		return USER_DETAILS;
	}

	/**
	 * Returns the table name for events.
	 * 
	 * @return the table name for events
	 */
	public static String getEvents() {
		return EVENTS;
	}

	/**
	 * Returns the table name for user events.
	 * 
	 * @return the table name for user events
	 */
	public static String getUserEvents() {
		return USER_EVENTS;
	}

	/**
	 * Returns the admin username.
	 * 
	 * @return the admin username
	 */
	public static String getAdminUsername() {
		return ADMIN_USERNAME;
	}

	/**
	 * Returns the admin password.
	 * 
	 * @return the admin password
	 */
	public static String getAdminPassword() {
		return ADMIN_PASSWORD;
	}
}
