package com.ricex.cartracker.data.mapper.auth;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.auth.RegistrationKey;
import com.ricex.cartracker.data.mapper.EntityMapper;

public interface RegistrationKeyMapper extends EntityMapper<RegistrationKey> {

	/** Fetches the registration key that matches the given key
	 * 
	 * @param key The key
	 * @return the registration key
	 */
	
	public RegistrationKey getByKey(String key);
	
	public RegistrationKey getByKey(@Param("key") String key, @Param("orderBy") String orderBy, RowBounds bounds);
	
}
