package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.UserEventsModel;

public class ManageRegisteredUsersController implements Initializable {

	@FXML
	private TableView userTable;

	@FXML
	private TableColumn user_id, user_name, event_name, event_price;

	@FXML
	private TextField userID, userName, eventName, eventPrice;

	@FXML
	private Label errMsg;

	private ObservableList<Map> eventDataList = FXCollections.observableArrayList();

	private UserEventsModel userEventsObj;

	// Constructor
	public ManageRegisteredUsersController() throws SQLException {
		userEventsObj = new UserEventsModel();
	}

	// Initialize method
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Create user events table if not exists and refresh table view
		userEventsObj.createUserEventsTable();
		updateUserEventsTableView(userEventsObj.fetchUserEvents());

		// Set event handler for user table
		userTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				updateTextFieldsFromTable();
			}
		});
	}

	// Add User Event method
	@FXML
	public void addUserEvent(ActionEvent event) throws Exception {
		if (!fieldsAreEmpty()) {
			// Add user event to database and refresh table view
			userEventsObj.addUserEvent(userName.getText(), eventName.getText(),
					Double.parseDouble(eventPrice.getText()));
			updateUserEventsTableView(userEventsObj.fetchUserEvents());
			errMsg.setText("New User Event added Successfully!");
			refreshTextFields();
		} else {
			errMsg.setText("No Data Entered");
		}
	}

	// Update User Event method
	@FXML
	public void updateUserEvent(ActionEvent event) throws SQLException {
		String user_name = userName.getText();
		String user_event = eventName.getText();
		String event_price = eventPrice.getText();

		if (!fieldsAreEmpty()) {
			int id = Integer.parseInt(userID.getText());
			// Update user event in database and refresh table view
			userEventsObj.updateUserEvent(id, user_name, user_event, event_price);
			updateUserEventsTableView(userEventsObj.fetchUserEvents());
			errMsg.setText("User Event details updated successfully!");
			refreshTextFields();
		} else {
			errMsg.setText("No Record Selected to Update");
		}
	}

	// Delete User Event method
	@FXML
	public void deleteUserEvent(ActionEvent event) throws SQLException {
		if (!(userID.getText().isEmpty())) {
			// Delete user event from database and refresh table view
			userEventsObj.deleteEvent(Integer.parseInt(userID.getText()));
			updateUserEventsTableView(userEventsObj.fetchUserEvents());
			errMsg.setText("User Event Deleted Successfully!");
			refreshTextFields();
		} else {
			errMsg.setText("No Record Selected to Delete");
		}
	}

	// Go to Admin Dashboard method
	@FXML
	public void goToAdminDashboard(ActionEvent event) throws IOException {
		switchScene("/views/AdminDashboardView.fxml", "Admin Dashboard", event);
	}

	// Logout method
	@FXML
	public void logout(ActionEvent event) throws IOException {
		switchScene("/views/AdminLoginView.fxml", "Admin Login Page", event);
	}

	// Refresh user events table view with data from result set
	public void updateUserEventsTableView(ResultSet rs) {
		try {
			// Clear the existing data in eventDataList
			eventDataList.clear();
			
			while (rs.next()) {
				// Create a map for each row of data and add it to the observable list
				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("user_id", rs.getInt(1) + "");
				dataRow.put("user_name", rs.getString(2));
				dataRow.put("event_name", rs.getString(3));
				dataRow.put("event_price", rs.getString(4));
				eventDataList.add(dataRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Set cell value factories and set the items of the table view
		user_id.setCellValueFactory(new MapValueFactory("user_id"));
		user_name.setCellValueFactory(new MapValueFactory("user_name"));
		event_name.setCellValueFactory(new MapValueFactory("event_name"));
		event_price.setCellValueFactory(new MapValueFactory("event_price"));
		userTable.setItems(eventDataList);
	}

	// Set cell value from table to text field
	private void updateTextFieldsFromTable() {
		if (userTable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = userTable.getSelectionModel().getSelectedItem();

			// Parse selected row data and set it to text fields
			userID.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			userName.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceFirst("\\s+", ""));
			eventName.setText((selectedItems.toString().split(",")[3].substring(
					selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[3].indexOf("}"))).replaceFirst("\\s+", ""));
			eventPrice.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
		}
	}

	// Refresh text fields
	private void refreshTextFields() {
		userID.setText("");
		userName.setText("");
		eventName.setText("");
		eventPrice.setText("");
	}

	// Check if input fields are empty
	private boolean fieldsAreEmpty() {
		return userName.getText().isEmpty() || eventName.getText().isEmpty() || eventPrice.getText().isEmpty();
	}

	// Switch scene method
	private void switchScene(String fxmlPath, String title, ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
		centerStage(stage);
	}

	// Center stage on screen
	private void centerStage(Stage stage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
}
