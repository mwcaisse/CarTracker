package com.ricex.cartracker.web.controller.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.data.manager.ReadingManager;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;

@Controller
@RequestMapping("/api")
public class ReadingController extends ApiController<Reading> {
	
	private final ReadingManager manager;
	
	/** Creates a new Reading Controller with the specific Reading Manager to perform
	 * 		data access
	 * @param manager The manager to perform data access
	 */
	
	public ReadingController(ReadingManager manager) {
		super(EntityType.READING, manager);
		this.manager = manager;
	}
	/** Fetches the reading with the given id
	 * 
	 * @param id The id of the reading
	 * @return The reading
	 */
	
	@RequestMapping(value="/reading/{id}", method = RequestMethod.GET, produces={JSON})			
	public @ResponseBody EntityResponse<Reading> get(@PathVariable long id) {
		return super.get(id);
	}
	
	/** Fetches all of the reading for a particular trip
	 * 
	 * @param tripId The id of the trip
	 * @return The readings
	 */
	@RequestMapping(value="/trip/{tripId}/reading/", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<List<Reading>> getAllForTrip(@PathVariable long tripId) {
		return createEntityResponse(manager.getForTrip(tripId));
	}
	
	/** Performs a bulk upload of readings
	 * 
	 * @param tripId The id of the trip to upload the readings to
	 * @param readings The readings to upload
	 * @return Result of each reading upload
	 */
	
	@RequestMapping(value = "/trip/{tripId}/reading/bulk", method=RequestMethod.POST, produces={JSON}, consumes={JSON})
	public @ResponseBody EntityResponse<List<BulkUploadResult>> bulkUpload(@PathVariable long tripId, @RequestBody ReadingUpload[] readings) {
		try {
			return createEntityResponse(manager.bulkUpload(tripId, Arrays.asList(readings)));
		} catch (EntityValidationException e) {
			return createEntityResponseError(e);
		}
	}

}
