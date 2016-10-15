package com.ricex.cartracker.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.AbstractEntity;

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
	
	
	/** Gets a list of all the entities, offset and limited by the row bounds
	 * 
	 * @param orderBy The order by string
	 * @param rb The row bounds containing the limit and offset
	 * @return The entities within the limit and offset
	 */
	public List<T> getAll(@Param("orderBy")  String orderBy, RowBounds rb);
	
	/** Gets the total count of all the entities
	 * 
	 * @return The total number of entities
	 */	
	public long countAll();
	
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
	
	/** Deletes the entity with the given id
	 * 
	 * @param id The id of the entity to delete
	 */
	public void delete(long id);
	
}
