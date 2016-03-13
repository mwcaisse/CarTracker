package com.ricex.cartracker.data.validation;

import com.ricex.cartracker.data.entity.Reading;

public class ReadingValidator implements EntityValidator<Reading> {

	/** Checks that the given Reading is valid
	 * 
	 * 		1) Read Date must be set
	 * 		2) Read Date must be in the past
	 * 		3) The trip must be set
	 */
	
	public void validate(Reading entity) throws EntityValidationException {
		
	}

}
