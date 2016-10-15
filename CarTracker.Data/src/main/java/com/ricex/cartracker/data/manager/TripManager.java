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
import com.ricex.cartracker.data.query.properties.EntityProperties;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.TripValidator;

public class TripManager extends AbstractEntityManager<Trip>  {	
	
	protected TripMapper mapper;
	protected TripValidator validator;
	protected CarManager carManager;
	
	public TripManager(TripMapper mapper, CarManager carManager) {
		this(mapper, carManager, new TripValidator());
	}
	
	public TripManager(TripMapper mapper, CarManager carManager, TripValidator validator) {
		super(mapper, validator, EntityType.TRIP);
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
	
	public Trip startTripForCar(String carVin) throws EntityValidationException {
		Trip trip = new Trip();
		
		Car car = carManager.getByVin(carVin);
		if (null == car) {
			throw new EntityValidationException(MessageFormat.format("The specified {0} does not exist!", EntityType.CAR.getName()));
		}
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
		Trip trip = get(id);
		if (null == trip) {
			throw new EntityValidationException(MessageFormat.format("{0} does not exist!", getEntityName()));
		}
		trip.setEndDate(new Date());
		trip.setModifiedDate(new Date());
		trip.setStatus(TripStatus.FINISHED);
		mapper.update(trip);
		
		return true;
	}
	
	
	protected void createValidationLogic(Trip toCreate) throws EntityValidationException {
		if (!carManager.exists(toCreate.getCarId())) {
			throw new EntityValidationException(MessageFormat.format("The specified {0} does not exist!", EntityType.CAR.getName()));
		}
	}
	
	protected void updateValidationLogic(Trip toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);
	}
}
