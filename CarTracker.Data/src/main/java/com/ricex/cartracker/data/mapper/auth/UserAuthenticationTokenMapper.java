package com.ricex.cartracker.data.mapper.auth;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;
import com.ricex.cartracker.data.mapper.EntityMapper;

public interface UserAuthenticationTokenMapper extends EntityMapper<UserAuthenticationToken> {

	/** Gets the User Authentication token by its token value
	 * 
	 * @param token The token value
	 * @return The token corresponding to the token value, or null if it doesn't exist
	 */
	
	public UserAuthenticationToken getByToken(String token);
	
	public List<UserAuthenticationToken> getActiveForUser(@Param("userId") long userId, RowBounds bounds);
	
	public List<UserAuthenticationToken> getActiveForUser(@Param("userId") long userId, @Param("orderBy") String orderBy, 
			RowBounds bounds);
	
	public long countActiveForUser(long userId);
	
}
