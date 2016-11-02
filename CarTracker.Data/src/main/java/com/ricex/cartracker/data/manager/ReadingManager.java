package com.ricex.cartracker.data.manager;

import java.util.ArrayList;
import java.util.List;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.ReadingValidator;
import com.ricex.cartracker.data.validation.TripValidator;

public class ReadingManager extends AbstractEntityManager<Reading> {

	private final ReadingMapper mapper;
	
	private final ReadingValidator validator;

	private final TripValidator tripValidator;
	
	public ReadingManager(ReadingMapper mapper, ReadingValidator validator, TripValidator tripValidator) {
		super(mapper, validator, EntityType.READING);		
		this.mapper = mapper;
		this.validator = validator;
		this.tripValidator = tripValidator;
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
	public List<BulkUploadResult> bulkUpload(long tripId, List<ReadingUpload> readings) throws EntityValidationException {
		List<BulkUploadResult> results = new ArrayList<BulkUploadResult>();
		
		tripValidator.exists(tripId);
		
		for (ReadingUpload upload : readings) {
			BulkUploadResult result = new BulkUploadResult();
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
		tripValidator.exists(toCreate.getTripId());
	}

	@Override
	protected void updateValidationLogic(Reading toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);
	}

}
