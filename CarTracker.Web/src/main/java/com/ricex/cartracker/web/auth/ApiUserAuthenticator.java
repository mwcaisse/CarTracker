package com.ricex.cartracker.web.auth;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import com.ricex.cartracker.common.auth.AuthToken;
import com.ricex.cartracker.common.auth.AuthUser;
import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;
import com.ricex.cartracker.data.manager.auth.UserAuthenticationTokenManager;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.web.auth.token.Token;
import com.ricex.cartracker.web.auth.token.TokenManager;

/** Authenticate users who log in through the API rather than the form login
 * 
 * @author Mitchell Caisse
 *
 */

public class ApiUserAuthenticator {

	private static Logger log = LoggerFactory.getLogger(ApiUserAuthenticator.class);
	
	private final AuthenticationManager authenticationManager;
	
	private final UserAuthenticationTokenManager authenticationTokenManager;
	
	private final TokenManager tokenManager;
	
	public ApiUserAuthenticator(AuthenticationManager authenticationManager, 
			UserAuthenticationTokenManager authenticationTokenManager, TokenManager tokenManager) {
		
		this.authenticationManager = authenticationManager;
		this.authenticationTokenManager= authenticationTokenManager;
		this.tokenManager = tokenManager;
	}
	
	/** Authenticates a user using their username/password and provides them with a Session Token
	 * 
	 * @param user The user's credentials
	 * @return The token that has been created for the user, if authentication is successful.
	 * @throws AuthenticationException If the user was unable to be authenticated
	 */
	
	public Token authenticateUser(AuthUser user) throws AuthenticationException {
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		auth = authenticationManager.authenticate(auth);
		return onAuthenticationSuccessful(auth);
	}
	
	
	/** Authenticates a user using thier username and authentication token and provides them with a Session Token
	 * 
	 * @param token The user's username/auth token
	 * @param clientAddress The remote address the user is logging in from
	 * @return The session token that has been created for the user, if authentication is successful
	 * @throws AuthenticationException If the user was unable to be authenticated
	 */
	public Token authenticateUser(AuthToken token, String clientAddress) throws AuthenticationException {
		Authentication auth = authenticateByToken(token, clientAddress);
		return onAuthenticationSuccessful(auth);
	}
	
	/** When authentication is successful, create a session token for the user
	 * 
	 * @param auth The user's successful authentication
	 * @return The user's session token
	 */
	
	private Token onAuthenticationSuccessful(Authentication auth) {
		log.info("User: {} successfully authenticated, granting them a session token!", auth.getName());
		return getTokenForUser(auth);
	}
	
	/** Performs the authentication using the given token
	 * 
	 * @param token The token to authenticate with
	 * @return The authentication if successful
	 * @throws AuthenticationException If authentication was unsuccessful
	 */
	
	private Authentication authenticateByToken(AuthToken token, String clientAddress) throws AuthenticationException {
		UserAuthenticationToken authToken = authenticationTokenManager.getByToken(token.getAuthenticationToken());
		if (null == authToken) {
			throw new PreAuthenticatedCredentialsNotFoundException("The provided Authentication Token is not valid.");
		}
		
		//check that the authentication token is for the correct device and user
		if (!StringUtils.equals(authToken.getDeviceUuid(), token.getDeviceUuid()) ||
				authToken.getUserId() == UserAuthenticationToken.INVALID_ID ||
				!StringUtils.equalsIgnoreCase(authToken.getUser().getUsername(), token.getUsername())) {
			throw new BadCredentialsException("The provided Authentication Token is not valid.");
		}
		
		if (!authToken.isValid()) {
			throw new DisabledException("The provided Authentication Token has been disabled.");
		}
		
		authToken.setLastLogin(new Date());
		authToken.setLastLoginAddress(clientAddress);
		try {
			authenticationTokenManager.update(authToken);
		}
		catch (EntityValidationException e) {
			throw new AuthenticationServiceException("Unable to process authentication token.");
		}	
		
		TokenAuthenticationToken auth = new TokenAuthenticationToken(authToken);
		auth.setAuthenticated(true);
		return auth;
	}
	
	/** Creates or fetches an existing token for the user with the given Authentication
	 * 
	 * @param auth The user's authentication
	 * @return The token for the user
	 */
	private Token getTokenForUser(Authentication auth) {
		String username = auth.getName();
		
		Token token = tokenManager.getTokenForUser(username);
		if (null == token) {
			token = tokenManager.createToken(username, auth);
		}
		
		return token;
	}
	
	
	
}
