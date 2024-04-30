package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.UserDetailModel;

public class UserLoginController {

	// Injecting JavaFX components defined in the FXML file using @FXML annotation
	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Button login;

	@FXML
	private Hyperlink signup;

	@FXML
	private Label errmsg;

	// User ID variable
	int userID = 0;

	// Instance of UserDetailModel for user data operations
	private UserDetailModel userDetail;

	// Constructor
	public UserLoginController() throws SQLException {
		userDetail = new UserDetailModel();
	}

	// Method triggered when login button is pressed
	@FXML
	public void userLogin(ActionEvent event) throws Exception {
		// Retrieve username and password from input fields
		String username = this.username.getText();
		String password = this.password.getText();

		// Perform login validation
		userLoginValidation(username, password);

		// Check if the user exists in the database
		boolean flag = userDetail.searchUserDetailsDB(username, new SHAHashingController().hashPassword(password));

		if (flag) {
			// If user exists, redirect to user dashboard
			userID = userDetail.getIdFromUserDetailsDB(username, password);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserDashboardView.fxml"));
			Parent parent = (Parent) loader.load();
			UserDetailController userAction = loader.getController();
			userAction.setUserName(username);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(parent));
			stage.setTitle("User Dashboard");
			stage.show();

			// Center the stage on the screen
			centerStage(stage);
		} else {
			// If user does not exist, display error message
			errmsg.setText("Invalid username or password. Please try again.");
		}
	}

	// Method to perform login input validation
	public void userLoginValidation(String username, String password) {
		errmsg.setText(""); // Clear any previous error messages

		// Check for empty or whitespace-only username
		if (username == null || username.trim().equals("")) {
			errmsg.setText("Username Cannot be empty or have spaces");
			return;
		}

		// Check for empty or whitespace-only password
		if (password == null || password.trim().equals("")) {
			errmsg.setText("Password Cannot be empty or have spaces");
			return;
		}

		// Check if both username and password are empty or whitespace-only
		if (username.trim().isEmpty() && password.trim().isEmpty()) {
			errmsg.setText("Username and password cannot be empty.");
			return;
		}
	}

	// Method triggered when signup hyperlink is clicked
	public void userSignUp(ActionEvent event) throws IOException, SQLException {
		// Switch to the user sign-up page
		switchScene("/views/UserSignUpView.fxml", "User SignUp Page", event);
	}

	// Method triggered when home hyperlink is clicked
	public void goToHome(ActionEvent event) throws IOException {
		// Switch to the welcome page
		switchScene("/views/WelcomeView.fxml", "Welcome Page", event);
	}

	// Method to switch scenes
	private void switchScene(String fxmlPath, String title, ActionEvent event) throws IOException {
		// Load the FXML file for the new scene
		Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
		// Center the stage on the screen
		centerStage(stage);
	}

	// Method to center the stage on the screen
	private void centerStage(Stage stage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
}
