package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
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
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.EventsModel;
import models.UserEventsModel;

public class UserDetailController implements Initializable {

	// Define FXML elements
	@FXML
	private TableView eventTable;

	@FXML
	private TableView userEventTable;

	@FXML
	private TableColumn event_id, event_name, price, event_desc;

	@FXML
	private TableColumn user_event_id, user_event_name, user_event_price;

	@FXML
	private Label username, errMsg, registerStatus;

	String userName;

	// Define observable lists to hold data
	ObservableList<Map> tableDataList = FXCollections.observableArrayList();
	ObservableList<Map> eventDataList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize event table view with data from the database
		EventsModel eventDBObj;
		try {
			eventDBObj = new EventsModel();
			updateEventsTableView(eventDBObj.fetchEventListFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Add selected event to user's list
	@FXML
	private void addEvent(ActionEvent event) {
		Object selectedItems = eventTable.getSelectionModel().getSelectedItem();
		if (selectedItems != null) {
			// Extract data from selected event and add to user's list
			Map<String, String> eventdatarow = new HashMap<>();
			eventdatarow.put("event_id",
					(selectedItems.toString().split(",")[1]
							.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1))
							.replaceAll("\\s+", ""));
			eventdatarow.put("event_name",
					((selectedItems.toString().split(",")[3].substring(
							selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
							selectedItems.toString().split(",")[3].indexOf("}"))).replaceFirst("\\s+", "")));
			eventdatarow.put("price", (selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)));
			eventDataList.add(eventdatarow);

			// Update user event table view
			user_event_id.setCellValueFactory(new MapValueFactory("event_id"));
			user_event_name.setCellValueFactory(new MapValueFactory("event_name"));
			user_event_price.setCellValueFactory(new MapValueFactory("price"));
			userEventTable.setItems(eventDataList);
			errMsg.setText("");
		} else if (selectedItems == null) {
			errMsg.setText("Please select an event item to add");
		}
	}

	// Set username label
	public void setUserName(String name) {
		username.setText(name);
		userName = username.getText();
	}

	// Remove selected event from user's list
	@FXML
	private void removeEvent(ActionEvent event) {
		Object selectedRowForDeletion = userEventTable.getSelectionModel().getSelectedItem();

		if (selectedRowForDeletion != null) {
			userEventTable.getItems().removeAll(userEventTable.getSelectionModel().getSelectedItem());
			errMsg.setText("");
		} else {
			errMsg.setText("No Event Selected to remove");
		}
	}

	// Register user for selected events
	@FXML
	public void registerUser(ActionEvent event) throws NumberFormatException, SQLException {
		UserEventsModel userEvent = new UserEventsModel();
		Iterator<HashMap<String, String>> itr = userEventTable.getItems().iterator();

		while (itr.hasNext()) {
			HashMap<String, String> hm = itr.next();
			userEvent.addUserEvent(userName, hm.get("event_name"), Double.parseDouble(hm.get("price")));

		}
		registerStatus.setText("User Event Registered Successfully!");
	}

	// Refresh event table view with updated data
	public void updateEventsTableView(ResultSet rs) {
		try {
			while (rs.next()) {
				// Add data from ResultSet to observable list
				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("event_id", rs.getInt(1) + "");
				dataRow.put("event_name", rs.getString(2));
				dataRow.put("price", rs.getString(3));
				dataRow.put("event_desc", rs.getString(4));
				tableDataList.add(dataRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Set cell value factories and set data to event table view
		event_id.setCellValueFactory(new MapValueFactory("event_id"));
		event_name.setCellValueFactory(new MapValueFactory("event_name"));
		price.setCellValueFactory(new MapValueFactory("price"));
		event_desc.setCellValueFactory(new MapValueFactory("event_desc"));
		eventTable.setItems(tableDataList);
	}

	// Logout user and navigate to login page
	@FXML
	public void logout(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/views/UserLoginView.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("User Login Page");
		stage.show();

		// Center the login page on the screen
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
}
