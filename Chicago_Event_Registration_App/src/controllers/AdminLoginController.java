package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminLoginController extends AdminInterfaceController {

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Button login, home;

	@FXML
	private Label errMsg;

	// Method to handle login button click event
	@Override
	@FXML
	public void adminLogin(ActionEvent event) {
		// Retrieving admin credentials
		String adminUsername = Constants.getAdminUsername();
		String adminPassword = Constants.getAdminPassword();

		// Getting entered username and password
		String enteredUsername = username.getText();
		String enteredPassword = password.getText();

		// Checking if entered credentials match admin credentials
		if (adminUsername.equals(enteredUsername) && adminPassword.equals(enteredPassword)) {
			try {
				// Load admin dashboard view
				Parent parent = FXMLLoader.load(getClass().getResource("/views/AdminDashboardView.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.setTitle("Admin Dashboard");

				// Centering the stage on the screen
				centerStage(stage);

				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// Display error message for wrong credentials
			errMsg.setText("Incorrect credentials. Please try again.");
			// Clearing username and password fields
			clearFields();
		}
	}

	// Method to handle home button click event
	@Override
	@FXML
	public void goToHome(ActionEvent event) {
		try {
			// Load welcome view
			Parent parent = FXMLLoader.load(getClass().getResource("/views/WelcomeView.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Welcome Page");

			// Centering the stage on the screen
			centerStage(stage);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to center the stage on the screen
	private void centerStage(Stage stage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	// Method to clear username and password fields
	private void clearFields() {
		username.clear();
		password.clear();
	}
}
