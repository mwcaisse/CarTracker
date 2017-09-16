package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.CarSupportedCommands;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.entity.CarSupportedCommandsViewModel;
import com.ricex.cartracker.data.manager.CarSupportedCommandsManager;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;

@Controller
@RequestMapping("api/car/")
public class CarSupportedCommandsController extends ApiController<CarSupportedCommands> {

	private final CarSupportedCommandsManager manager;
	
	public CarSupportedCommandsController(CarSupportedCommandsManager manager) {
		super(EntityType.CAR_SUPPORTED_COMMANDS, manager);
		this.manager = manager;
	}	
	
	@RequestMapping(value = "/{carId}/supportedCommands/", method=RequestMethod.GET, produces = {JSON})
	public @ResponseBody EntityResponse<CarSupportedCommandsViewModel> getViewModel(@PathVariable long carId) {
		CarSupportedCommands supportedCommands = manager.getForCar(carId);
		return createEntityResponse(new CarSupportedCommandsViewModel(supportedCommands));
	}
	
	@RequestMapping(value = "/{vin}/supportedCommands/", method = RequestMethod.POST, produces = {JSON})
	public @ResponseBody BooleanResponse createOrUpdate(@PathVariable String vin, 
			@RequestBody CarSupportedCommands supportedCommands) {
		try {
			return new BooleanResponse(manager.createOrUpdate(vin, supportedCommands));
		}
		catch (EntityValidationException e) {
			return new BooleanResponse(e.getMessage());
		}
	}
	
}
