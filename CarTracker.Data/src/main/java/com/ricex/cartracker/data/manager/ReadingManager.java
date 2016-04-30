package com.ricex.cartracker.data.manager;

import java.util.ArrayList;
import java.util.List;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;
import com.ricex.cartracker.common.viewmodel.ReadingUploadResult;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.ReadingValidator;

public class ReadingManager extends AbstractEntityManager<Reading> {

	public static final String ENTITY_NAME = "Reading";
	
	private ReadingMapper mapper;
	
	private ReadingValidator validator;
	
	private TripManager tripManager;
	
	public ReadingManager(ReadingMapper mapper, TripManager tripManager) {
		this (mapper, tripManager, new ReadingValidator());
	}
	
	protected ReadingManager(ReadingMapper mapper, TripManager tripManager, ReadingValidator validator) {
		super(mapper, validator);
		
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
			throw new EntityValidationException("The specified trip does not exist!");
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
			throw new EntityValidationException(String.format("The specified {0} does not exist!", tripManager.ENTITY_NAME));
		}
	}

	@Override
	protected void updateValidationLogic(Reading toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

}
