package com.ricex.cartracker.data.validation.auth;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;
import com.ricex.cartracker.data.mapper.auth.UserAuthenticationTokenMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.AbstractEntityValidator;
import com.ricex.cartracker.data.validation.EntityValidationException;

public class UserAuthenticationTokenValidator extends AbstractEntityValidator<UserAuthenticationToken> {

	private final UserAuthenticationTokenMapper mapper;
	
	public UserAuthenticationTokenValidator(UserAuthenticationTokenMapper mapper) {
		super(mapper, EntityType.USER_AUTHENTICATION_TOKEN);
		this.mapper = mapper;
	}

	public void validate(UserAuthenticationToken entity) throws EntityValidationException {
		if (entity.getUserId() == UserAuthenticationToken.INVALID_ID) {
			throw new EntityValidationException("The associated user cannot be blank!");
		}
		if (StringUtils.isBlank(entity.getToken())) {
			throw new EntityValidationException("The token value cannot be blank!");
		}
		if (StringUtils.isBlank(entity.getDeviceUuid())) {
			throw new EntityValidationException("The Device UUID cannot be blank!");
		}
		
	}

}
