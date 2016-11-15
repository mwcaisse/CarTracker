package com.ricex.cartracker.data.mapper;

import java.util.List;

import com.ricex.cartracker.common.entity.RegistrationKeyUse;

public interface RegistrationKeyUseMapper extends EntityMapper<RegistrationKeyUse> {

	public List<RegistrationKeyUse> getAllForKey(long keyId);
	
	public long countAllForKey(long keyId);
	
}
