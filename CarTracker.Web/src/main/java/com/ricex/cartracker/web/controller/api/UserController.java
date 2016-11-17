package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.auth.User;
import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.UserRegistration;
import com.ricex.cartracker.data.manager.auth.UserAuthenticationTokenManager;
import com.ricex.cartracker.data.manager.auth.UserManager;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.web.auth.ApiUserAuthenticator;

@Controller
@RequestMapping("/api/user")
public class UserController extends ApiController<User> {

	private final UserManager manager;
	
	private final UserAuthenticationTokenManager authenticationTokenManager;
	
	private final ApiUserAuthenticator userAuthenticator;
	
	public UserController(UserManager manager, UserAuthenticationTokenManager authenticationTokenManager,
			ApiUserAuthenticator userAuthenticator) {
		super(EntityType.USER, manager);
		
		this.manager = manager;
		this.authenticationTokenManager = authenticationTokenManager;
		this.userAuthenticator = userAuthenticator;
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
	
	/** Creates an authentication token for the currently logged in user
	 * 
	 * @param deviceUuid The UUID of the device to create the token for
	 * @return The value of the created token
	 */
	@RequestMapping(value = "/token", method = RequestMethod.POST, produces={JSON})
	public @ResponseBody EntityResponse<String> createAuthenticationToken(@RequestBody String deviceUuid) {
		try {
			UserAuthenticationToken token = authenticationTokenManager.generateToken(getCurrentUser().getId(), deviceUuid);
			return createEntityResponse(token.getToken());
		}
		catch (EntityValidationException e) {
			return createEntityResponseError(e);
		}
	}
	
	

}
