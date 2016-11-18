package com.ricex.cartracker.androidrequester.request.tracker;

import java.util.List;

import com.ricex.cartracker.androidrequester.request.AbstractRequestFactory;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.user.AuthenticationTokenRequest;
import com.ricex.cartracker.androidrequester.request.user.LoginPasswordRequest;
import com.ricex.cartracker.androidrequester.request.user.LoginTokenRequest;
import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.ReaderLog;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.ReaderLogUpload;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;

public class CarTrackerRequestFactory extends AbstractRequestFactory {

	public CarTrackerRequestFactory(ApplicationPreferences applicationPreferences) {
		super(applicationPreferences);
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

	/** Creates a new Bulk Upload Reader Log Request
	 *
	 * @param logs The logs to upload
	 * @return The request
     */
	public BulkUploadReaderLogRequest createBulkUploadReaderLogRequest(List<ReaderLogUpload> logs) {
		return new BulkUploadReaderLogRequest(applicationPreferences, logs);
	}
	
	/** Creates a new Create Car Request
	 * 
	 * @param car The car to create
	 * @return The request
	 */
	public CreateCarRequest createCreateCarRequest(Car car) {
		return new CreateCarRequest(applicationPreferences, car);
	}

	/** Creates a new Create Trip Request
	 *
	 * @param trip The trip to create
	 * @return The request
     */
	public CreateTripRequest createCreateTripRequest(Trip trip) {
		return new CreateTripRequest(applicationPreferences, trip);
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

	/** Creates a new Create Reader Log Request
	 *
	 * @param readerLog The reader log to create
	 * @return The request
     */
	public CreateReaderLogRequest createReaderLogRequest(ReaderLog readerLog) {
		return new CreateReaderLogRequest(applicationPreferences, readerLog);
	}

	/** Creates a Login Password Request
	 *
	 * @param username The username
	 * @param password The password
     * @return
     */
	public LoginPasswordRequest createLoginPasswordRequest(String username, String password) {
		return new LoginPasswordRequest(applicationPreferences, username, password);
	}

	/** Creates a new Login Token Request
	 *
	 * @param username
	 * @param token
     * @return
     */
	public LoginTokenRequest createLoginTokenRequest(String username, String token) {
		return new LoginTokenRequest(applicationPreferences, username, token);
	}

	/** Creates a new Authentication Token Request
	 *
	 * @return
     */
	public AuthenticationTokenRequest createAuthenticationTokenRequest() {
		return new AuthenticationTokenRequest(applicationPreferences);
	}
	
}
