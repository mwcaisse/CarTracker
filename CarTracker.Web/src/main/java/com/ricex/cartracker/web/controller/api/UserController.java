package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.User;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.UserRegistration;
import com.ricex.cartracker.data.manager.UserManager;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;

@Controller
@RequestMapping("/api/user")
public class UserController extends ApiController<User> {

	private final UserManager manager;
	
	public UserController(UserManager manager) {
		super(EntityType.USER, manager);
		
		this.manager = manager;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST, consumes={JSON}, produces={JSON})
	public @ResponseBody EntityResponse<Boolean> register(@RequestBody UserRegistration registration) {
		try {
			manager.register(registration);
			return new BooleanResponse(true);
		}
		catch (EntityValidationException e) {
			return createEntityResponseError(e);
		}
	}
	
	@RequestMapping(value="/available", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody BooleanResponse isUsernameAvailable(@RequestParam String username) {
		return new BooleanResponse(manager.isUsernameAvailable(username));
	}

}
