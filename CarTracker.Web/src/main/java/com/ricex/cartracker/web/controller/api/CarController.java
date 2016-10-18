package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.manager.CarManager;
import com.ricex.cartracker.data.validation.EntityValidationException;

@Controller
@RequestMapping("/api/car")
public class CarController extends ApiController<Car> {

	private static final String ENTITY_NAME = "Car";
	
	private final CarManager manager;
	
	public CarController(CarManager manager) {
		super(ENTITY_NAME, manager);
		this.manager = manager;
	}
	
	/** Returns the Car with the specified Id
	 * 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<Car> get(@PathVariable long id) {
		return super.get(id);
	}
	
	/** Returns the car with the given VIN
	 * 
	 * @param vin The VIN of the car
	 * @return The car with the given VIN
	 */
	@RequestMapping(value="/vin/{vin}", method=RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<Car> getByVin(@PathVariable String vin) {
		Car car = manager.getByVin(vin);
		//TODO: Do I want to return a 404 here? Would allow for better programmatic response handling..
		return createEntityResponseErrorIfNull(car, String.format("No car with the VIN: {0} exists!", vin));
	}
	
	
	/** Returns all of the cars
	 * 
	 * @return All of the cars
	 */
	@RequestMapping(value="/", method=RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<PagedEntity<Car>> getAll(
			@RequestParam(name = "startAt", required = false, defaultValue = DEFAULT_START_AT) int startAt,
			@RequestParam(name = "maxResults", required = false, defaultValue = DEFAULT_MAX_RESULTS) int maxResults,
			SortParam sort) {				
		return super.getAll(startAt, maxResults, sort);
	}
	
	/** Checks if a car with the given VIN is registered or not
	 * 
	 * @param vin The VIN to check
	 * @return True if it is registered false otherwise
	 */
	@RequestMapping(value ="/registered/{vin}", method=RequestMethod.GET, produces={JSON})
	public @ResponseBody BooleanResponse isRegistered(@PathVariable String vin) {
		return new BooleanResponse(manager.existsByVin(vin));
	}
	
	/** Creates the given car
	 * 
	 * @param car The car to create
	 * @return The created car, or an error message stating why it couldn't be created
	 */
	@RequestMapping(value = "/", method=RequestMethod.POST, produces={JSON}, consumes={JSON})
	public @ResponseBody EntityResponse<Car> create(@RequestBody Car car) {
		return super.create(car);
	}
	
	/** Updates the given car
	 * 
	 * @param car The car to update
	 * @return The updated car, or an error message stating why it couldn't be updated
	 */
	@RequestMapping(value ="/", method=RequestMethod.PUT, produces={JSON}, consumes={JSON})
	public @ResponseBody BooleanResponse update(@RequestBody Car car) {
		return super.update(car);
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
	

}
