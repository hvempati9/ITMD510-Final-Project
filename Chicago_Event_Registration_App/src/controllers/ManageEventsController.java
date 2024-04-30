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
import models.EventsModel;

public class ManageEventsController implements Initializable {

	// UI elements
	@FXML
	private TableView eventsTable;

	@FXML
	private TableColumn event_id, event_name, event_price, event_desc;

	@FXML
	private TextField m_eventId, m_eventName, m_eventPrice, m_eventDesc;

	@FXML
	private Label errMsg;

	// ObservableList to store table data
	ObservableList<Map> eventDataList = FXCollections.observableArrayList();

	// Model for database operations
	private EventsModel eventsObj;

	// Constructor
	public ManageEventsController() throws SQLException {
		eventsObj = new EventsModel();
	}

	// Initialize method
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// Create events table in the database if it does not exist
			eventsObj.createEventsTable();
			// Refresh the TableView with data from the database
			updateEventsTableView(eventsObj.fetchEventListFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Set action for single mouse click on TableView
		eventsTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				// Update text fields with selected row data
				updateTextFieldsFromTable();
			}
		});
	}

	// Add event method
	@FXML
	public void addEvent(ActionEvent event) throws Exception {
		if (!fieldsAreEmpty()) {
			// Insert new event record into the database
			eventsObj.insertEventRecords(m_eventName.getText(), Double.parseDouble(m_eventPrice.getText()),
					m_eventDesc.getText());
			// Refresh TableView with updated data
			updateEventsTableView(eventsObj.fetchEventListFromDB());
			errMsg.setText("New Event added successfully!");
			// Clear input fields
			refreshTextFields();
		} else {
			errMsg.setText("No Data Entered");
		}
	}

	// Update event method
	@FXML
	public void updateEvent(ActionEvent event) throws SQLException {
		String name = m_eventName.getText();
		String price = m_eventPrice.getText();
		String desc = m_eventDesc.getText();

		try {
			if (!fieldsAreEmpty()) {
				int id = Integer.parseInt(m_eventId.getText());
				// Update existing event record in the database
				eventsObj.updateEvent(id, name, price, desc);

				updateEventsTableView(eventsObj.fetchEventListFromDB());
				errMsg.setText("Event data updated successfully!");
				refreshTextFields();
			} else {
				errMsg.setText("No Record Selected to Update");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Delete event method
	@FXML
	public void deleteEvent(ActionEvent event) {
		try {
			if (!m_eventId.getText().isEmpty()) {
				// Delete selected event record from the database
				eventsObj.deleteEvent(Integer.parseInt(m_eventId.getText()));
				updateEventsTableView(eventsObj.fetchEventListFromDB());
				errMsg.setText("Event data deleted Successfully!");
				refreshTextFields();
			} else {
				errMsg.setText("No Record Selected to Delete");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Switch to Admin Dashboard
	@FXML
	public void goToAdminDashboard(ActionEvent event) throws IOException {
		switchScene("/views/AdminDashboardView.fxml", "Admin Dashboard", event);
	}

	// Logout and switch to Admin Login Page
	@FXML
	public void logout(ActionEvent event) throws IOException {
		switchScene("/views/AdminLoginView.fxml", "Admin Login Page", event);
	}

	// Refresh TableView with data from ResultSet
	public void updateEventsTableView(ResultSet rs) {
		try {
			// Clear the existing data in eventDataList
			eventDataList.clear();

			while (rs.next()) {
				// Create a map to store row data
				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("event_id", rs.getInt(1) + "");
				dataRow.put("event_name", rs.getString(2));
				dataRow.put("event_price", rs.getString(3));
				dataRow.put("event_desc", rs.getString(4));

				// Add the map to the ObservableList
				eventDataList.add(dataRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Set cell value factories for each column
		event_id.setCellValueFactory(new MapValueFactory<>("event_id"));
		event_name.setCellValueFactory(new MapValueFactory<>("event_name"));
		event_price.setCellValueFactory(new MapValueFactory<>("event_price"));
		event_desc.setCellValueFactory(new MapValueFactory<>("event_desc"));
		// Set the items of the TableView
		eventsTable.setItems(eventDataList);
	}

	// Update text fields with data from selected row in TableView
	private void updateTextFieldsFromTable() {
		if (eventsTable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = eventsTable.getSelectionModel().getSelectedItem();

			// Extract and set values to text fields
			m_eventId.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceFirst("\\s+", ""));
			m_eventName.setText((selectedItems.toString().split(",")[3].substring(
					selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[3].indexOf("}"))).replaceFirst("\\s+", ""));
			m_eventPrice.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			m_eventDesc.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
		}
	}

	// Clear input fields
	private void refreshTextFields() {
		m_eventId.setText("");
		m_eventName.setText("");
		m_eventPrice.setText("");
		m_eventDesc.setText("");
	}

	// Check if input fields are empty
	private boolean fieldsAreEmpty() {
		return m_eventName.getText().isEmpty() || m_eventPrice.getText().isEmpty() || m_eventDesc.getText().isEmpty();
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
