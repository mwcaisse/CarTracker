package com.ricex.cartracker.data.validation;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.User;
import com.ricex.cartracker.data.mapper.UserMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class UserValidator extends AbstractEntityValidator<User> {

	private final UserMapper mapper;
	
	public UserValidator(UserMapper mapper) {
		super(mapper, EntityType.USER);
		
		this.mapper = mapper;
	}

	public void validate(User entity) throws EntityValidationException {		
		if (StringUtils.isBlank(entity.getUsername())) {
			throw new EntityValidationException("Username cannot be blank!");
		}
		if (StringUtils.contains(entity.getUsername(), " ")) {
			throw new EntityValidationException("Username cannot contain spaces!");
		}
		if (StringUtils.isBlank(entity.getPassword())) {
			throw new EntityValidationException("Password cannot be blank!");
		}
		if (StringUtils.isBlank(entity.getEmail())) {
			throw new EntityValidationException("Email cannot be blank!");
		}
		if (StringUtils.contains(entity.getEmail(), " ")) {
			throw new EntityValidationException("Email cannot contain spaces!");
		}
	}

}
