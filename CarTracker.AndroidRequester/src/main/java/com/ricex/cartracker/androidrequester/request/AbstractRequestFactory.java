package com.ricex.cartracker.androidrequester.request;

import java.util.List;

import com.ricex.cartracker.androidrequester.request.tracker.BulkUploadReadingRequest;
import com.ricex.cartracker.androidrequester.request.tracker.CreateCarRequest;
import com.ricex.cartracker.androidrequester.request.tracker.EndTripRequest;
import com.ricex.cartracker.androidrequester.request.tracker.IsCarRegisteredRequest;
import com.ricex.cartracker.androidrequester.request.tracker.StartTripRequest;
import com.ricex.cartracker.androidrequester.request.user.AuthenticationTokenRequest;
import com.ricex.cartracker.androidrequester.request.user.LoginPasswordRequest;
import com.ricex.cartracker.androidrequester.request.user.LoginTokenRequest;
import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;

public abstract class AbstractRequestFactory {

	/** The Application Preferences to use for all Requests
	 * 
	 */
	protected ApplicationPreferences applicationPreferences;
	
	/** Creates a new Abstract Request Factory with the given application preferences
	 * 
	 * @param applicationPreferences The application preferences
	 */
	
	public AbstractRequestFactory(ApplicationPreferences applicationPreferences) {
		this.applicationPreferences = applicationPreferences;
	}
	
	/** Creates a new Authentication Token Request
	 * 
	 * @return the request
	 */
	public AuthenticationTokenRequest createAuthenticationTokenRequest() {
		return new AuthenticationTokenRequest(applicationPreferences);
	}
	
	/** Creates a new Login Password Request, with the given username + password
	 * 
	 * @param username The username
	 * @param password The password
	 * @return the request
	 */
	public LoginPasswordRequest createLoginPasswordRequest(String username, String password) {
		return new LoginPasswordRequest(applicationPreferences, username, password);
	}
	
	/** Creates a new Login Token Request
	 * 
	 * @param token The login token
	 * @return The request
	 */
	public LoginTokenRequest createLoginTokenRequest(String token) {
		return new LoginTokenRequest(applicationPreferences, token);
	}
	
	/** Creates a new Bulk Upload Reading Request
	 * 
	 * @param tripId The id of the trip to upload the readings to
	 * @param readings The readings to upload
	 * @return The request
	 */
	public BulkUploadReadingRequest createBulkUploadReadingRequest(long tripId, List<ReadingUpload> readings) {
		return new BulkUploadReadingRequest(applicationPreferences, tripId, readings);
	}
	
	/** Creates a new Bulk Upload Reading Request
	 * 
	 * @param trip The trip to upload the readings to
	 * @param readings The readings to upload
	 * @return The request
	 */
	public BulkUploadReadingRequest createBulkUploadReadingRequest(Trip trip, List<ReadingUpload> readings) {
		return new BulkUploadReadingRequest(applicationPreferences, trip, readings);
	}
	
	/** Creates a new Create Car Request
	 * 
	 * @param car The car to create
	 * @return The request
	 */
	public CreateCarRequest createCreateCarRequest(Car car) {
		return new CreateCarRequest(applicationPreferences, car);
	}
	
	/** Creates a new End Trip Request
	 * 
	 * @param trip The trip to end
	 * @return The request
	 */
	public EndTripRequest createEndTripRequest(Trip trip) {
		return new EndTripRequest(applicationPreferences, trip);
	}
	
	/** Creates a new End Trip Request
	 * 
	 * @param tripId The id of the trip to create
	 * @return The request
	 */
	public EndTripRequest createEndTripRequest(long tripId) {
		return new EndTripRequest(applicationPreferences, tripId);				
	}
	
	/** Creates a new Is Car Registered Request
	 * 
	 * @param vin The vin of the car to check
	 * @return The request
	 */
	public IsCarRegisteredRequest createIsCarRegisteredRequest(String vin) {
		return new IsCarRegisteredRequest(applicationPreferences, vin);
	}
	
	/** Creates a new Start Trip Request
	 * 
	 * @param car The car to start the trip for
	 * @return The request
	 */
	public StartTripRequest createStartTripRequest(Car car) {
		return new StartTripRequest(applicationPreferences, car);
	}
	
	/** Creates a new Start Trip Request
	 * 
	 * @param vin The VIN of the car to start the trip for
	 * @return The request
	 */
	public StartTripRequest createStartTripRequest(String vin) {
		return new StartTripRequest(applicationPreferences, vin);				
	}
	
	
}
