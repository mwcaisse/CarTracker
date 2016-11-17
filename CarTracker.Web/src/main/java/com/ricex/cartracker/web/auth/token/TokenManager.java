package com.ricex.cartracker.web.auth.token;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;

public class TokenManager {

	private Map<String, Token> tokens;
	
	public TokenManager() {
		tokens = new HashMap<String, Token>();
	}
	
	/** Gets the token with the specified Id
	 * 
	 * @param id The id of the token to fetch
	 * @return The token with the given ID or null if no valid token with that id exists
	 */
	
	public Token getToken(String id) {
		Token token = tokens.get(id);
		
		if (isTokenValid(token)) {
			return token;
		}
		return null;
	}
	
	
	private boolean isTokenValid(Token token) {
		if (null != token && token.isExpired()) {
			tokens.remove(token.getId());
			return false;
		}
		return null != token;
	}
	
	public Token getTokenForUser(String username) {
		for (Token token : tokens.values()) {
			if (token.getUsername().equals(username)) {
				if (isTokenValid(token)) {
					return token;
				}
			}
		}
		return null; // no valid tokens for the user found
	}
	
	public Token createToken(String username, Authentication auth) {
		Token token = new CarTrackerToken(createTokenId(), username, auth, getExpirationDate());
		tokens.put(token.getId(), token);
		return token;
	}
	
	private String createTokenId() {
		return UUID.randomUUID().toString();
	}
	
	private Date getExpirationDate() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR_OF_DAY, 1);
		return now.getTime();
	}
	
}
