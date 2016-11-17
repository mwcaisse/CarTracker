package com.ricex.cartracker.data.mapper.auth;

import java.util.List;

import com.ricex.cartracker.common.entity.auth.RegistrationKeyUse;
import com.ricex.cartracker.data.mapper.EntityMapper;

public interface RegistrationKeyUseMapper extends EntityMapper<RegistrationKeyUse> {

	public List<RegistrationKeyUse> getAllForKey(long keyId);
	
	public long countAllForKey(long keyId);
	
}
