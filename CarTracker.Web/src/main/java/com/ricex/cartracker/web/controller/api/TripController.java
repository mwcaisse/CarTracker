package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.manager.TripManager;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.web.processor.TripProcessor;

@Controller
@RequestMapping("/api")
public class TripController extends ApiController<Trip> {

	private static final String ENTITY_NAME = "Car";
	
	private final TripManager manager;
	
	private final TripProcessor tripProcessor;
	
	public TripController(TripManager manager,TripProcessor tripProcessor) {
		super(ENTITY_NAME, manager);
		this.manager = manager;
		this.tripProcessor = tripProcessor;
	}
	
	/** Fetches the trip with the given id
	 * 
	 * @param id The id of the trip to fetch
	 * @return The trip
	 */
	
	@RequestMapping(value="/trip/{id}", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<Trip> get(@PathVariable long id) {
		return super.get(id);
	}
	
	/** Fetches all of the Trips. Supports paging
	 * 
	 * @param startAt the starting index, optional defaults to 0
	 * @param maxResults  the maximum number of results, optional defaults to 25
	 * @return The trips
	 */
	@RequestMapping(value="/trip/", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<PagedEntity<Trip>> getAll(
			@RequestParam(name = "startAt", required = false, defaultValue = "0") int startAt, 
			@RequestParam(name = "maxResults", required = false, defaultValue = "25") int maxResults) {
		return super.getAll(startAt, maxResults);
	}
	
	/** Fetches all of the trips for the given car
	 * 
	 * @param carId The id of the car
	 * @return List of trips for the car
	 */
	@RequestMapping(value="/car/{carId}/trip/", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<PagedEntity<Trip>> getAllForCar(@PathVariable long carId,
			@RequestParam(name = "startAt", required = false, defaultValue = DEFAULT_START_AT) int startAt, 
			@RequestParam(name = "maxResults", required = false, defaultValue = DEFAULT_MAX_RESULTS) int maxResults,
			SortParam sort) { 
		
		return createEntityResponse(manager.getForCar(carId, startAt, maxResults, sort));
	}
	
	/** Creates a new Trip for the given car
	 * 
	 * @param carId The id of the car to create the trip for
	 * @param toCreate The Trip to create
	 * @return The created trip
	 */
	@RequestMapping(value="/car/{carId}/trip/", method = RequestMethod.POST, consumes={JSON}, produces={JSON})
	public @ResponseBody EntityResponse<Trip> createForCar(@PathVariable long carId, @RequestBody Trip toCreate) {
		toCreate.setCarId(carId);
		return super.create(toCreate);
	}
	
	/** Creates a new Trip.
	 * 
	 * @param toCreate The trip to create
	 * @return The created trip
	 */
	@RequestMapping(value="/trip/", method=RequestMethod.POST, consumes={JSON}, produces={JSON})
	public @ResponseBody EntityResponse<Trip> create(@RequestBody Trip toCreate) {
		return super.create(toCreate);
	}
	
	/** Updates the given trip
	 * 
	 * @param toUpdate The trip to update
	 * @return True if successful false / error otherwise
	 */
	@RequestMapping(value="/trip/", method=RequestMethod.PUT, consumes={JSON}, produces={JSON})
	public @ResponseBody BooleanResponse update(@RequestBody Trip toUpdate) {
		return super.update(toUpdate);
	}
	
	/** Starts a trip for the given car
	 * 
	 * @param carVin The VIN of the car to start the trip for
	 * @return The newly created trip
	 */
	@RequestMapping(value="/car/{carVin}/trip/start", method=RequestMethod.POST, produces={JSON})
	public @ResponseBody EntityResponse<Trip> startTrip(@PathVariable String carVin) {
		try {
			return createEntityResponse(manager.startTripForCar(carVin));
		} 
		catch (EntityValidationException e) {
			return createEntityResponseError(e);
		}
	}
	
	/** Ends the given trip
	 * 
	 * @param id The id of the trip to end
	 * @return True if trip was ended, false otherwise
	 */
	
	@RequestMapping(value="/trip/{id}/end", method=RequestMethod.POST, produces={JSON})
	public @ResponseBody BooleanResponse endTrip(@PathVariable long id) {
		try {
			boolean response = manager.endTrip(id);
			return new BooleanResponse(response);			
		} 
		catch (EntityValidationException e) {
			return new BooleanResponse(e.getMessage());
		}
	}
	
	/** Processes the trip with the given id
	 * 
	 * @param id The id of the trip to process
	 * @return The processed trip
	 */
	@RequestMapping(value ="/trip/{id}/process", method=RequestMethod.POST, produces={JSON})
	public @ResponseBody EntityResponse<Trip> processTrip(@PathVariable long id) {
		try {
			return createEntityResponse(tripProcessor.processTrip(id));
		}
		catch (EntityValidationException e) {
			return createEntityResponseError(e);
		}
	}
}
