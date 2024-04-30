package controllers;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ContactValidationController implements javafx.beans.value.ChangeListener<String> {

	private int maxLength; // Maximum length allowed for the text field
	private TextField contact; // The text field to validate

	// Constructor
	public ContactValidationController(TextField contact, int maxLength) {
		this.contact = contact;
		this.maxLength = maxLength;
	}

	// Getter method for retrieving the maximum allowed length
	public int getMaxLength() {
		return maxLength;
	}

	// Method called when the value of the text field changes
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		// Check if the new value is not null and exceeds the maximum allowed length
		if (newValue != null && newValue.length() > maxLength) {
			// Truncate the value to the maximum allowed length
			String truncatedValue = newValue.substring(0, maxLength);
			// Update the text field with the truncated value
			contact.setText(truncatedValue);
		}
	}
}
