package com.ricex.cartracker.web.controller.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.data.entity.Trip;
import com.ricex.cartracker.data.manager.TripManager;

@Controller
@RequestMapping("/api")
public class TripController extends ApiController<Trip> {

	private static final String ENTITY_NAME = "Car";
	
	private final TripManager manager;
	
	public TripController(TripManager manager) {
		super(ENTITY_NAME, manager);
		this.manager = manager;
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
	
	/** Fetches all of the Trips
	 * 
	 * @return A list of all the trips
	 */
	@RequestMapping(value="/trip/", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<List<Trip>> getAll() {
		return super.getAll();
	}
	
	/** Fetches all of the trips for the given car
	 * 
	 * @param carId The id of the car
	 * @return List of trips for the car
	 */
	@RequestMapping(value="/car/{carId}/trip/", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<List<Trip>> getAllForCar(@PathVariable long carId) {
		return createEntityResponse(manager.getForCar(carId));
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
	 * @return True if sucessful false / error otherwise
	 */
	public @ResponseBody BooleanResponse update(@RequestBody Trip toUpdate) {
		return super.update(toUpdate);
	}
}
