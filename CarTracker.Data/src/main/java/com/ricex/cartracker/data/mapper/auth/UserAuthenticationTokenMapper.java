package com.ricex.cartracker.data.mapper.auth;

import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;
import com.ricex.cartracker.data.mapper.EntityMapper;

public interface UserAuthenticationTokenMapper extends EntityMapper<UserAuthenticationToken> {

	/** Gets the User Authentication token by its token value
	 * 
	 * @param token The token value
	 * @return The token corresponding to the token value, or null if it doesn't exist
	 */
	
	public UserAuthenticationToken getByToken(String token);
	
}
