package controllers;

import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WelcomeController implements Initializable {

	@FXML
	private Hyperlink userlogin, adminlogin, help;

	@FXML
	private ImageView chicago;

	// Method to handle user login action
	public void userLogin(ActionEvent event) throws IOException {
		loadPage("/views/UserLoginView.fxml", "User Login Page", event);
	}

	// Method to handle admin login action
	public void adminLogin(ActionEvent event) throws IOException {
		loadPage("/views/AdminLoginView.fxml", "Admin Login Page", event);
	}

	// Method to handle help action
	public void help(ActionEvent event) throws IOException {
		loadPage("/views/HelpView.fxml", "Help Page", event);
	}

	// Method to load a new page
	private void loadPage(String resourcePath, String pageTitle, ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(resourcePath));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(pageTitle);
		stage.show();

		// Center the stage on the screen
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	// Initialize method to load image
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// Load image using resource loading
			InputStream stream = getClass().getResourceAsStream("/welcome.jpg");
			if (stream != null) {
				Image chicagoEvent = new Image(stream);
				this.chicago.setImage(chicagoEvent);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Print stack trace for any exceptions
		}
	}
}
