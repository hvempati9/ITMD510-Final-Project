package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HelpController extends Application implements Initializable {

	@FXML
	private ImageView help;

	// Method to navigate to the home page
	@FXML
	private void goToHome(ActionEvent event) throws IOException {
		switchToScene("/views/WelcomeView.fxml", "Welcome Page", event);
	}

	// Method to navigate to the admin login page
	@FXML
	private void goToAdminLogin(ActionEvent event) throws IOException {
		switchToScene("/views/AdminLoginView.fxml", "Admin Login Page", event);
	}

	// Method to navigate to the user login page
	@FXML
	private void goToUserLogin(ActionEvent event) throws IOException {
		switchToScene("/views/UserLoginView.fxml", "User Login Page", event);
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HelpView.fxml"));
		Group root = new Group();
		Scene scene = new Scene(root, 650, 650);
		loader.setController(this); // Set this controller for the FXML
		scene.setRoot(loader.load());
		primaryStage.setTitle("Help Page");
		primaryStage.setScene(scene);
		primaryStage.show();
		centerStage(primaryStage);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// Load image using resource loading
			InputStream stream = getClass().getResourceAsStream("/help.jpg");
			if (stream != null) {
				Image helpImage = new Image(stream);
				this.help.setImage(helpImage);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Print stack trace for any exceptions
		}
	}
}
