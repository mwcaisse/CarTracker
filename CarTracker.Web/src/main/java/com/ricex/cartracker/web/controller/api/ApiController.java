package com.ricex.cartracker.web.controller.api;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.ricex.cartracker.common.entity.AbstractEntity;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.manager.AbstractEntityManager;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.web.controller.BaseController;

public abstract class ApiController<T extends AbstractEntity> extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ApiController.class);

	protected static final String JSON = MediaType.APPLICATION_JSON_VALUE;
	
	protected static final String DEFAULT_START_AT = "0";
	protected static final String DEFAULT_MAX_RESULTS = "25";
	
	private AbstractEntityManager<T> manager;
	
	/** The entity type that this controller will be working with  */
	private EntityType entityType;
	
	/** Creates a new API Controller
	 * 
	 */
	
	public ApiController(EntityType entityType, AbstractEntityManager<T> manager) {
		this.entityType = entityType;
		this.manager = manager;
	}
	
	/** Fetches all of the entities of this type 
	 * 
	 * @return All of the entities of this type
	 */
	public EntityResponse<List<T>> getAll() {		
		return createEntityResponse(manager.getAll());
	}
	
	/** Fetches all of the entities of this type paged
	 * 
	 * @param startAt The start index
	 * @param maxResults The max number of results to return
	 * @return The entities on the page
	 */
	
	public EntityResponse<PagedEntity<T>> getAll(int startAt, int maxResults, SortParam sort) {
		return createEntityResponse(manager.getAll(startAt, maxResults, sort));
	}
	
	/** Fetches an entity with the specified id
	 * 
	 * @param id The id of the entity to fetch
	 * @return All of the entities or an appropriate error message
	 */
	public EntityResponse<T> get(long id) {	
		return createEntityResponseErrorIfNull(manager.get(id), MessageFormat.format("No {0} with that id exists!", entityType.getName()));
	}
	
	/** Determines if an entity with the given id exists
	 * 
	 * @param id The id of the entity to check
	 * @return True if it exists, false otherwise
	 */
	public BooleanResponse exists(long id) {
		return new BooleanResponse(manager.exists(id));
	}
	
	/** Creates the given entity
	 * 
	 * @param entity The entity to create
	 * @return The created entity or an error message if it failed 
	 */
	public EntityResponse<T> create(T entity) {
		try {
			manager.create(entity);
			return createEntityResponse(entity);
		}
		catch (EntityValidationException e) {
			return createEntityResponseError(e);
		}
	}
	
	/** Updates the given entity
	 * 
	 * @param entity The entity to update
	 * @return The updated entity if saved without issue, error message if it failed
	 */
	public EntityResponse<T>  update(T entity) {
		try {
			manager.update(entity);
			return createEntityResponse(entity);
		}
		catch (EntityValidationException e) {
			return createEntityResponseError(e);
		}
	}
	
	/** Creates an entity response to send data back to the user
	 * 
	 * @param data The data for the response
	 * @return The response
	 */
	protected <R> EntityResponse<R> createEntityResponse(R data) {	
		return new EntityResponse<R>(data);
	}
	
	/** Creates a new entity resposne with the given error message if null
	 * 
	 * @param data The response data
	 * @param message The error message if the response data is null
	 * @return The created response
	 */
	protected <R> EntityResponse<R> createEntityResponseErrorIfNull(R data, String message) {
		if (null == data) {
			return new EntityResponse<R>(data, message);
		}
		return createEntityResponse(data);
	}
	
	/** Creates a new entity response for the given Entity Validation Exception
	 * 
	 * @param e The Entity Validation Exception to create the response for
	 * @return The entity response
	 */
	protected <R> EntityResponse<R> createEntityResponseError(EntityValidationException e) {
		return new EntityResponse<R>(null, e.getMessage());
	}
	
}
