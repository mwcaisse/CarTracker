package com.ricex.cartracker.data.validation;

import com.ricex.cartracker.common.entity.AbstractEntity;

public interface EntityValidator<T extends AbstractEntity> {

	
	/** Validates the given entity
	 * 
	 * @param entity The entity to validate
	 * @throws EntityValidationException Thrown when there is a validation error, the message contains
	 * 		the validation issue
	 */
	public void validate(T entity) throws EntityValidationException;
	
	/** Checks that an entity with the given ID exists. Throws an exception if the entity doesn't exist
	 * 
	 * @param id The ID of the entity to check for existence
	 * @throws EntityValidationException If the entity does not exist
	 */
	public void exists(long id) throws EntityValidationException;
	
	
}
