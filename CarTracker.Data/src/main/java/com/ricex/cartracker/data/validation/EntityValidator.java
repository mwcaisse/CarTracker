package com.ricex.cartracker.data.validation;

import com.ricex.cartracker.data.entity.AbstractEntity;

public interface EntityValidator<T extends AbstractEntity> {

	
	/** Validates the given entity
	 * 
	 * @param entity The entity to validate
	 * @throws EntityValidationException Thrown when there is a validation error, the message contains
	 * 		the validation issue
	 */
	public void validate(T entity) throws EntityValidationException;
	
	
}
