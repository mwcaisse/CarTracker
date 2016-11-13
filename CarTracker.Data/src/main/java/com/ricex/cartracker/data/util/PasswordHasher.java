package com.ricex.cartracker.data.util;

public interface PasswordHasher {

	/** Creates a hthe hash for the given password
	 * 
	 * @param password The password to hash
	 * @return The hash for the password
	 */
	public String hashPassword(String password);
	
}
