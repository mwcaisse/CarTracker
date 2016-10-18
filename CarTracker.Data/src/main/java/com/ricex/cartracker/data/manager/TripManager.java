package com.ricex.cartracker.data.manager;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.entity.TripStatus;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.mapper.TripMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.CarValidator;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.TripValidator;

public class TripManager extends AbstractEntityManager<Trip>  {	
	
	protected TripMapper mapper;
	protected TripValidator validator;
	protected CarValidator carValidator;
	
	public TripManager(TripMapper mapper, TripValidator validator, CarValidator carValidator) {
		super(mapper, validator, EntityType.TRIP);
		this.mapper = mapper;
		this.validator = validator;
		this.carValidator = carValidator;
	}
	
	/** Gets all of the trips associated with the given car
	 * 
	 * @param carId The car's Id
	 * @return The trips for the car
	 */
	public List<Trip> getForCar(long carId) {
		return mapper.getForCar(carId);
	}
	
	/** Gets all of the trips associated with the given car
	 * 
	 * @param carId The car's Id
	 * @return The trips for the car
	 */
	public PagedEntity<Trip> getForCar(long carId, int startAt, int maxResults, SortParam sort) {
		String oderBy = parseSortBy(sort);
		List<Trip> trips = mapper.getForCar(carId, oderBy, new RowBounds(startAt, maxResults));
		long totalTripCount = mapper.countForCar(carId);
		return new PagedEntity<Trip>(trips, startAt, maxResults, totalTripCount);
	}
	
	/** Gets all of the unprocessed trips
	 * 
	 * @return All of the unprocessed trips
	 */
	public List<Trip> getUnprocessedTrips() {
		return mapper.getUnprocessedTrips();
	}
	
	/** Creates a new trip for the car with the given VIN
	 * 
	 * @param carVin The VIN of the car to create the trip for
	 * @return The created trip
	 * @throws EntityValidationException 
	 */
	
	public Trip startTripForCar(Car car) throws EntityValidationException {		
		carValidator.exists(car.getId()); //validate that the car exists
		
		Trip trip = new Trip();		
		trip.setCar(car);
		trip.setStartDate(new Date());
		trip.setStatus(TripStatus.STARTED);
		
		mapper.create(trip);	
		return trip;
	}
	
	/** Ends the given trip
	 * 
	 * @param id The Id of the trip to end
	 * @return True
	 * @throws EntityValidationException If a trip with given Id does not exist
	 */
	
	public boolean endTrip(long id) throws EntityValidationException {
		validator.exists(id); //validate that the trip exists
		Trip trip = get(id);
		trip.setEndDate(new Date());
		trip.setModifiedDate(new Date());
		trip.setStatus(TripStatus.FINISHED);
		mapper.update(trip);
		
		return true;
	}
	
	
	protected void createValidationLogic(Trip toCreate) throws EntityValidationException {
		carValidator.exists(toCreate.getCarId());
	}
	
	protected void updateValidationLogic(Trip toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);
	}
}
