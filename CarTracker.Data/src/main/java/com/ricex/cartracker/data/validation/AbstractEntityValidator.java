package com.ricex.cartracker.data.validation;

import java.text.MessageFormat;

import com.ricex.cartracker.common.entity.AbstractEntity;
import com.ricex.cartracker.data.mapper.EntityMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public abstract class AbstractEntityValidator<T extends AbstractEntity> implements EntityValidator<T> {

	private final EntityMapper<T> mapper;
	private final EntityType entityType;
	
	public AbstractEntityValidator(EntityMapper<T> mapper, EntityType entityType) {
		this.mapper = mapper;
		this.entityType = entityType;
	}
	
	/** Checks that an entity with the given ID exists. Throws an exception if the entity doesn't exist
	 * 
	 * @param id The ID of the entity to check for existence
	 * @throws EntityValidationException If the entity does not exist
	 */
	public void exists(long id) throws EntityValidationException {
		if (null == mapper.get(id)) {
			throw new EntityValidationException(MessageFormat.format("The specified {0} does not exist!", entityType.getName()));
		}
	}
	
}
