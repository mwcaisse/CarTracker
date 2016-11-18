package com.ricex.cartracker.androidrequester.request;

public interface ApplicationPreferences {

	/** Returns the base Server Address that API calls will be made against
	 * 
	 * @return The base server address
	 */
	public String getServerAddress();
	
	/** Returns the UID of the device we are running on
	 * 
	 * @return The Device's UID
	 */
	public String getDeviceUID();

	/** Returns the username for the user
	 *
	 * @return The user's username
     */
	public String getUsername();

	
	/** Returns the saved authentication token
	 * 
	 * @return The saved authentication token
	 */
	public String getAuthToken();
}
