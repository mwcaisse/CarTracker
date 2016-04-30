package com.ricex.cartracker.data.manager;

import java.util.Date;
import java.util.List;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.data.mapper.TripMapper;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.TripValidator;

public class TripManager extends AbstractEntityManager<Trip>  {

	public static final String ENTITY_NAME = "Trip";
	
	protected TripMapper mapper;
	protected TripValidator validator;
	protected CarManager carManager;
	
	public TripManager(TripMapper mapper, CarManager carManager) {
		this(mapper, carManager, new TripValidator());
	}
	
	public TripManager(TripMapper mapper, CarManager carManager, TripValidator validator) {
		super(mapper, validator);
		this.mapper = mapper;
		this.validator = validator;
		this.carManager = carManager;
	}
	
	/** Gets all of the trips associated with the given car
	 * 
	 * @param carId The car's Id
	 * @return The trips for the car
	 */
	public List<Trip> getForCar(long carId) {
		return mapper.getForCar(carId);
	}
	
	/** Creates a new trip for the car with the given VIN
	 * 
	 * @param carVin The VIN of the car to create the trip for
	 * @return The created trip
	 * @throws EntityValidationException 
	 */
	
	public Trip startTripForCar(String carVin) throws EntityValidationException {
		Trip trip = new Trip();
		
		Car car = carManager.getByVin(carVin);
		if (null == car) {
			throw new EntityValidationException("The specified car does not exist!");
		}
		trip.setCar(car);
		trip.setStartDate(new Date());
		
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
		Trip trip = get(id);
		if (null == trip) {
			throw new EntityValidationException("Trip does not exist!");
		}
		trip.setEndDate(new Date());
		trip.setModifiedDate(new Date());
		mapper.update(trip);
		
		return true;
	}
	
	
	protected void createValidationLogic(Trip toCreate) throws EntityValidationException {
		if (!carManager.exists(toCreate.getCarId())) {
			throw new EntityValidationException("The specified car does not exist!");
		}
	}
	
	protected void updateValidationLogic(Trip toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);
	}
	
	protected String getEntityName() {
		return ENTITY_NAME;
	}	
}
