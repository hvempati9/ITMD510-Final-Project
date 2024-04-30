package controllers;

import javafx.event.ActionEvent;

// Abstract class representing the interface for admin-related actions
public abstract class AdminInterfaceController {

	// Abstract method to handle admin login
	public abstract void adminLogin(ActionEvent event);

	// Abstract method to navigate to the home page
	public abstract void goToHome(ActionEvent event);
}
