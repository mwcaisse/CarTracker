package com.ricex.cartracker.web.auth.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import com.ricex.cartracker.common.auth.TokenAuthentication;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	
	private final TokenManager tokenManager;	
	
	public TokenAuthenticationFilter(TokenManager tokenManager, AuthenticationManager authenticationManager) {
		super(new RequestHeaderRequestMatcher(TokenAuthentication.SESSION_TOKEN_HEADER));
		
		this.tokenManager = tokenManager;		
		super.setAuthenticationManager(authenticationManager);
	}

	/** Pull the authentication token from the header and authenticate the user if it is valid
	 * 
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		String tokenId = request.getHeader(TokenAuthentication.SESSION_TOKEN_HEADER);
		
		if (StringUtils.isBlank(tokenId)) {
			return null; // no token was present in the header, move on
		}
		
		Token token = tokenManager.getToken(tokenId);
		if (null != token) {
			return token.getAuthentication();
		}
		
		//no valid token was found, clear the security context and send back an error
		SecurityContextHolder.clearContext();
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: Token is invalid  or expired.");
		
		//no auth to return
		return null;
	}
	
	/** Override the default successful authentication behavior to continue processing the request after
	 * 		authentication is successful
	 */
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication auth) throws IOException, ServletException {
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
	}

}
