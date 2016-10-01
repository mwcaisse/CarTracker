package com.ricex.cartracker.data.manager;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.AbstractEntity;
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
	
	public List<T> getAllPaged(int startAt, int maxResults) {
		return entityMapper.getAll(new RowBounds(startAt, maxResults));
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
		createValidationLogic(toCreate);
		entityMapper.create(toCreate);
		return toCreate;
	}
	
	/** Updates the given entity
	 * 
	 * @param toUpdate The entity to update
	 * @throws EntityValidationException If the entity is not valid an exception is thrown, with the reasoning in the message
	 */
	public void update(T toUpdate) throws EntityValidationException {
		//check to make sure that the entity exists
		T existing = get(toUpdate.getId());
		if (null == existing) {
			throw new EntityValidationException(String.format("This %s does not exist!", getEntityName()));
		}
		entityValidator.validate(toUpdate);
		updateValidationLogic(toUpdate);
		toUpdate.setModifiedDate(new Date());
		entityMapper.update(toUpdate);
	}
	
	public boolean exists(long id) {
		return null != get(id);
	}	
	
	/** Performs any required logic for validating that creating this entity is allowed.
	 * 
	 *  Performed after/if the entity passes validation
	 * 
	 * @param toCreate The entity to create
	 * @throws EntityValidationException If this entity cannot be created
	 */
	protected abstract void createValidationLogic(T toCreate) throws EntityValidationException;
	
	/** Performs any required logic for validating that performing the specified updates to the entity is allowed.
	 * 
	 * 	Performed after/if the entity passes validation
	 * 
	 * @param toUpdate The entity being updated
	 * @throws EntityValidationException If the the update to this entity cannot be performed
	 */
	
	protected abstract void updateValidationLogic(T toUpdate) throws EntityValidationException;
	
	/** Returns the name of the entity that this manager operates on
	 * 
	 * @return The entity name
	 */
	protected abstract String getEntityName();

}
