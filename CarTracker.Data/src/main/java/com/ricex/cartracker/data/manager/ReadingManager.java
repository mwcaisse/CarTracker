package com.ricex.cartracker.data.manager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;
import com.ricex.cartracker.common.viewmodel.ReadingUploadResult;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.ReadingValidator;

public class ReadingManager extends AbstractEntityManager<Reading> {

	private ReadingMapper mapper;
	
	private ReadingValidator validator;
	
	private TripManager tripManager;
	
	public ReadingManager(ReadingMapper mapper, TripManager tripManager) {
		this (mapper, tripManager, new ReadingValidator());
	}
	
	protected ReadingManager(ReadingMapper mapper, TripManager tripManager, ReadingValidator validator) {
		super(mapper, validator, EntityType.READING);
		
		this.mapper = mapper;
		this.validator = validator;
		this.tripManager = tripManager;
	}
	
	public List<Reading> getForTrip(long tripId) {
		return mapper.getForTrip(tripId);
	}
	
	
	/** Creates a series of Readings and records the results for each one
	 * 
	 * @param tripId The id of the trip to add the readings too
	 * @param readings The list of readings to create
	 * @return The results of the reading uploads
	 * @throws EntityValidationException If the given trip doesn't exist
	 */
	public List<ReadingUploadResult> bulkUpload(long tripId, List<ReadingUpload> readings) throws EntityValidationException {
		List<ReadingUploadResult> results = new ArrayList<ReadingUploadResult>();
		
		Trip trip = tripManager.get(tripId);
		if (null == trip) {
			throw new EntityValidationException(MessageFormat.format("The specified {0} does not exist!", EntityType.TRIP.getName()));
		}
		
		for (ReadingUpload upload : readings) {
			ReadingUploadResult result = new ReadingUploadResult();
			result.setUuid(upload.getUuid());
			try {
				create(upload);
				result.setId(upload.getId());
				result.setSuccessful(true);
			}
			catch (EntityValidationException e) {
				result.setSuccessful(false);
				result.setErrorMessage(e.getMessage());
			}
			
			results.add(result);
		}
		
		return results;
	}
	

	@Override
	protected void createValidationLogic(Reading toCreate) throws EntityValidationException {
		if (!tripManager.exists(toCreate.getTripId())) {
			throw new EntityValidationException(MessageFormat.format("The specified {0} does not exist!", EntityType.TRIP.getName()));
		}
	}

	@Override
	protected void updateValidationLogic(Reading toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);
	}

}
