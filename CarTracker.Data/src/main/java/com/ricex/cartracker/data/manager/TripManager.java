package com.ricex.cartracker.data.manager;

import java.util.List;

import com.ricex.cartracker.data.entity.Trip;
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
