package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import models.UserDetailModel;

public class UserSignUpController implements Initializable {

	@FXML
	private TextField name, email, address, contact;

	@FXML
	private PasswordField password1, password2;

	@FXML
	private Button signup;

	@FXML
	private Label nameerr, emailerr, addresserr, contacterr, pwd1err, pwd2err;

	// Method to check if a string is null or empty
	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	// Method to validate email format
	private boolean isValidEmail(String email) {
		return email != null
				&& (email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@hawk.iit.edu"));
	}

	// Method to validate phone number format
	private boolean isValidPhoneNumber(String phone) {
		return phone != null && phone.matches("^[0-9]{10}$");
	}

	// Method to check if passwords match
	private boolean isPasswordMatch(String password1, String password2) throws Exception {
		return new SHAHashingController().compareHashedPasswords(password1, password2);
	}

	// Event handler for user sign up button
	public void userSignUp(ActionEvent event) throws Exception {

		UserDetailModel userDetail = new UserDetailModel();

		// Validate all fields before proceeding
		boolean isValid = validateFields();

		if (isValid) {
			// Insert user details into the database
			boolean flag = userDetail.insertUserDetailRecords(name.getText(), email.getText(), address.getText(),
					contact.getText(), password1.getText());

			if (flag) {
				// Load the login page upon successful registration
				loadLoginPage(event);
			}
		}
	}

	// Method to validate all input fields
	private boolean validateFields() throws Exception {
		boolean isValid = true;

		isValid &= validateName();
		isValid &= validateEmail();
		isValid &= validateAddress();
		isValid &= validateContact();
		isValid &= validatePassword();

		return isValid;
	}

	// Method to validate name field
	private boolean validateName() {
		if (isNullOrEmpty(name.getText())) {
			nameerr.setText("Please enter your name");
			return false;
		} else {
			nameerr.setText("");
			return true;
		}
	}

	// Method to validate email field
	private boolean validateEmail() {
		if (isNullOrEmpty(email.getText())) {
			emailerr.setText("Please enter your email");
			return false;
		} else if (!isValidEmail(email.getText())) {
			emailerr.setText("Please enter a valid email address");
			return false;
		} else {
			emailerr.setText("");
			return true;
		}
	}

	// Method to validate address field
	private boolean validateAddress() {
		if (isNullOrEmpty(address.getText())) {
			addresserr.setText("Please enter your address");
			return false;
		} else {
			addresserr.setText("");
			return true;
		}
	}

	// Method to validate contact field
	private boolean validateContact() {
		if (isNullOrEmpty(contact.getText())) {
			contacterr.setText("Please enter your phone number");
			return false;
		} else if (!isValidPhoneNumber(contact.getText())) {
			contacterr.setText("Please enter a valid phone number");
			return false;
		} else {
			contacterr.setText("");
			return true;
		}
	}

	// Method to validate password fields
	private boolean validatePassword() throws Exception {
		if (isNullOrEmpty(password1.getText())) {
			pwd1err.setText("Please enter your password");
			return false;
		} else if (!isPasswordMatch(password1.getText(), password2.getText())) {
			pwd2err.setText("Passwords do not match");
			pwd1err.setText("");
			return false;
		} else {
			// If passwords match, hash and clear the error labels
			password1.setText(new SHAHashingController().hashPassword(password1.getText()));
			password2.setText(new SHAHashingController().hashPassword(password2.getText()));
			pwd1err.setText("");
			pwd2err.setText("");
			return true;
		}
	}

	// Method to load the login page
	private void loadLoginPage(ActionEvent event) throws IOException {
		switchToScene("/views/UserLoginView.fxml", "User Login Page", event);
	}

	// Initialize method to set up the controller
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Add listener for contact field to limit input length
		int maxLength = 10;
		contact.textProperty().addListener(new ContactValidationController(contact, maxLength));
	}

	// Event handler for returning to the home page
	@FXML
	private void goToHome(ActionEvent event) throws IOException {
		switchToScene("/views/WelcomeView.fxml", "Welcome Page", event);
	}

	// Common method to switch to a new scene
	private void switchToScene(String fxmlPath, String title, ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
		centerStage(stage);
	}

	// Method to center the stage on the screen
	private void centerStage(Stage stage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
}
