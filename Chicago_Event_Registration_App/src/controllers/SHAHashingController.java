package controllers;

import java.security.MessageDigest;

public class SHAHashingController {

	// Method to encode a password using SHA-256 hashing algorithm
	public String hashPassword(String password) throws Exception {

		// Create MessageDigest instance for SHA-256
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		// Add password bytes to digest
		md.update(password.getBytes());

		// Get the hashed bytes
		byte byteData[] = md.digest();

		// Convert the byte data to hexadecimal format
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			// Convert byte to hex string, ensure two digits
			hexString.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		// Return the hashed password as a string
		return hexString.toString();
	}

	// Method to check if two hashed passwords match
	public boolean compareHashedPasswords(String password1, String password2) throws Exception {

		// Hash the first password
		SHAHashingController encoder1 = new SHAHashingController();
		String hash1 = encoder1.hashPassword(password1);

		// Hash the second password
		SHAHashingController encoder2 = new SHAHashingController();
		String hash2 = encoder2.hashPassword(password2);

		// Compare the hashed passwords
		if (hash1.equalsIgnoreCase(hash2))
			return true;
		else
			return false;
	}
}
