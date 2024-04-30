package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AdminDashboardController implements Initializable {

	@FXML
	private ImageView adminImg;

	// Switch to the Manage Events view
	@FXML
	public void editEvents(ActionEvent event) throws IOException {
		switchScene("/views/ManageEventsView.fxml", "Events Page", event);
	}

	// Switch to the Manage Users view
	@FXML
	public void editUsers(ActionEvent event) throws IOException {
		switchScene("/views/ManageRegisteredUsersView.fxml", "Registered Users Page", event);
	}

	// Logout and switch to the Admin Login view
	@FXML
	public void logout(ActionEvent event) throws IOException {
		switchScene("/views/AdminLoginView.fxml", "Admin Login Page", event);
	}

	// Initialize the controller
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// Load admin image
			InputStream stream = getClass().getResourceAsStream("/admin.jpg");
			if (stream != null) {
				Image admin = new Image(stream);
				this.adminImg.setImage(admin);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle any exceptions
		}
	}

	// Switch scene utility method
	private void switchScene(String fxmlPath, String title, ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}
}
