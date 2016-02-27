package com.ricex.cartracker.data.mapper;

import java.util.List;

import com.ricex.cartracker.data.entity.AbstractEntity;

public interface EntityMapper<T extends AbstractEntity> {

	/** Gets the entity by its Id
	 * 
	 * @param id The id of the entity
	 * @return The entity, or null if none exists
	 */
	public T get(long id);
	
	/** Gets a list of all the entities
	 * 
	 * @return All of the entities, or empty list if none exist
	 */
	public List<T> getAll();	
	
	/** Creates the given entity
	 * 
	 * @param e The entity to create
	 */
	public void create(T e);
	
	/** Updates the given entity
	 * 
	 * @param e The entity to update
	 */
	public void update(T e);
	
}
