package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	// Global stage object to be accessed from other classes
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Set the global stage object
			stage = primaryStage;

			// Load the WelcomeView.fxml file using FXMLLoader
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/WelcomeView.fxml"));

			// Create a new scene with the root layout
			Scene scene = new Scene(root);

			// Add CSS styles to the scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Set the title of the stage
			stage.setTitle("Chicago Event Registration Application");

			// Set the scene to the stage
			stage.setScene(scene);

			// Show the stage
			stage.show();

		} catch (Exception e) {
			// Handle any exceptions that occur during the loading of the view
			System.out.println("Error occurred while inflating view: " + e);
		}
	}

	// Main method to launch the JavaFX application
	public static void main(String[] args) {
		launch(args);
	}
}
