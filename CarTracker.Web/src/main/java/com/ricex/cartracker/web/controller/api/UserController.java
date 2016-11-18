package com.ricex.cartracker.web.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.auth.AuthToken;
import com.ricex.cartracker.common.auth.AuthUser;
import com.ricex.cartracker.common.auth.TokenAuthentication;
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
import com.ricex.cartracker.web.auth.token.Token;

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
	
	@RequestMapping(value = "/login/password", method = RequestMethod.POST, produces = {JSON})
	public @ResponseBody BooleanResponse loginPassword(@RequestBody AuthUser user, HttpServletRequest request, 
			HttpServletResponse response) {		
		Token token = userAuthenticator.authenticateUser(user);
		return handleLoginResult(token, response);
	}
	
	@RequestMapping(value = "/login/token", method = RequestMethod.POST, produces = {JSON})
	public @ResponseBody BooleanResponse loginToken(@RequestBody AuthToken auth, HttpServletRequest request,
			HttpServletResponse response) {
		Token token = userAuthenticator.authenticateUser(auth, request.getRemoteAddr());
		return handleLoginResult(token, response);		
	}
	
	/** Handles the result of a user login.
	 * 
	 * If the authentication failed, then returns a false BooleanResponse.
	 * If the authentication passed, the adds the token header to response and returns a true BooleanResponse
	 * 
	 * @param token The token as the result of the login
	 * @param response Http response
	 * @return The boolean response to return to the user
	 */
	private BooleanResponse handleLoginResult(Token token, HttpServletResponse response) {
		if (null == token) {
			return new BooleanResponse(false);
		}
		else {
			response.addHeader(TokenAuthentication.SESSION_TOKEN_HEADER, token.getId());
			return new BooleanResponse(true);
		}
	}
	
	

}
