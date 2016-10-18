package com.ricex.cartracker.data.validation;



import java.util.Date;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.data.mapper.ReadingMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class ReadingValidator extends AbstractEntityValidator<Reading> {

	private final ReadingMapper mapper;
	
	public ReadingValidator(ReadingMapper mapper) {
		super(mapper, EntityType.READING);
		this.mapper = mapper;
	}
	
	/** Checks that the given Reading is valid
	 * 
	 * 		1) Read Date must be set
	 * 		2) Read Date must be in the past
	 * 		3) The trip must be set
	 */
	
	public void validate(Reading entity) throws EntityValidationException {
		if (null == entity.getReadDate()) {
			throw new EntityValidationException("Read Date cannot be blank!");
		}
		if (entity.getReadDate().after(new Date())) {
			throw new EntityValidationException("Read Date cannot be in the future!");
		}
		if (entity.getTripId() <= 0) {
			throw new EntityValidationException("Reading must be associated with a trip!");
		}
	}

}
