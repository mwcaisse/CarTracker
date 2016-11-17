package com.ricex.cartracker.data.mapper.auth;

import com.ricex.cartracker.common.entity.auth.User;
import com.ricex.cartracker.data.mapper.EntityMapper;

public interface UserMapper extends EntityMapper<User> {

	/** Returns the user, if any, with the given username
	 * 
	 * @param username The username to fetch by
	 * @return The user with that username or null if none exists
	 */
	public User getByUsername(String username);
}
