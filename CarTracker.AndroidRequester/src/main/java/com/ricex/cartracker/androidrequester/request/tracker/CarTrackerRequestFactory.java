package com.ricex.cartracker.androidrequester.request.tracker;

import java.util.List;

import com.ricex.cartracker.androidrequester.request.AbstractRequestFactory;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.Trip;
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