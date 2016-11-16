package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.RegistrationKey;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.manager.RegistrationKeyManager;
import com.ricex.cartracker.data.query.properties.EntityType;

@Controller
@RequestMapping("api/registration/key")
public class RegistrationKeyController extends ApiController <RegistrationKey> {

	private final RegistrationKeyManager manager;
	
	public RegistrationKeyController(RegistrationKeyManager manager) {
		super(EntityType.REGISTRATION_KEY, manager);

		this.manager = manager;
	}
	
	/** Returns the registration key with the given id
	 * 
	 * @param id The id of the registration key to fetch
	 * @return The registration key with the given id
	 */
	@RequestMapping(value = "/{id}", method=RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<RegistrationKey> get(@PathVariable long id) {
		return super.get(id);
	}
	
	/** Returns all of the registration keys with paging support
	 * 
	 * @param startAt The starting index
	 * @param maxResults The maximum number of results
	 * @param sort The sorting param
	 * @return The paged registration keys
	 */
	@RequestMapping(value="/",  method=RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<PagedEntity<RegistrationKey>> getAll(
			@RequestParam(name = "startAt", required = false, defaultValue = DEFAULT_START_AT) int startAt,
			@RequestParam(name = "maxResults", required = false, defaultValue = DEFAULT_MAX_RESULTS) int maxResults,
			SortParam sort) {
		
		return super.getAll(startAt, maxResults, sort);
	}
	
	/** Creates the given registration key
	 * 
	 * @param toCreate The Registration Key to create
	 * @return The created registration key or an error message stating why it couldn't be created
	 */
	@RequestMapping(value = "/", method=RequestMethod.POST, produces={JSON}, consumes = {JSON})
	public @ResponseBody EntityResponse<RegistrationKey> create(@RequestBody RegistrationKey toCreate) {
		return super.create(toCreate);
	}
	
	/** Updates the given registration key
	 * 
	 * @param toUpdate The registration key to update
	 * @return True if successful or an error message stating why it couldn't be updated
	 */
	@RequestMapping(value = "/", method=RequestMethod.PUT, produces={JSON}, consumes = {JSON})
	public @ResponseBody EntityResponse<RegistrationKey> update(@RequestBody RegistrationKey toUpdate) {
		return super.update(toUpdate);
	}

}
