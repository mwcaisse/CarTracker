package com.ricex.cartracker.data.manager;

import java.util.List;

import com.ricex.cartracker.data.entity.AbstractEntity;
import com.ricex.cartracker.data.mapper.EntityMapper;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.EntityValidator;

public abstract class AbstractEntityManager<T extends AbstractEntity> {
	
	
	protected EntityMapper<T> entityMapper;
	
	protected EntityValidator<T> entityValidator;
	
	protected AbstractEntityManager(EntityMapper<T> entityMapper, EntityValidator<T> entityValidator) {
		this.entityMapper = entityMapper;
		this.entityValidator = entityValidator;
	}		
	
	public List<T> getAll() {
		return entityMapper.getAll();
	}
	
	public T get(long id) {
		return entityMapper.get(id);
	}
	
	/** Creates the given entity
	 *
	 * @param toCreate The entity to create
	 * @return The created entity
	 * @throws EntityValidationException If the entity is not valid an exception is thrown, with the reason in the message
	 */
	public T create(T toCreate) throws EntityValidationException {
		toCreate.setNew();
		entityValidator.validate(toCreate);
		entityMapper.create(toCreate);
		return toCreate;
	}
	
	/** Updates the given entity
	 * 
	 * @param toUpdate The entity to update
	 * @throws EntityValidationException If the entity is not valid an exception is thrown, with the reasoning in the message
	 */
	public void update(T toUpdate) throws EntityValidationException {
		entityValidator.validate(toUpdate);
		entityMapper.update(toUpdate);
	}
	
	public boolean exists(long id) {
		return null != get(id);
	}

}
