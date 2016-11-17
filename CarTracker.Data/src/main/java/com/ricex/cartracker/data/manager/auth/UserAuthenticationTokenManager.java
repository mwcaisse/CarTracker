package com.ricex.cartracker.data.manager.auth;

import java.util.UUID;

import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;
import com.ricex.cartracker.data.manager.AbstractEntityManager;
import com.ricex.cartracker.data.mapper.auth.UserAuthenticationTokenMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.auth.UserAuthenticationTokenValidator;
import com.ricex.cartracker.data.validation.auth.UserValidator;

public class UserAuthenticationTokenManager extends AbstractEntityManager<UserAuthenticationToken> {

	private final UserAuthenticationTokenMapper mapper;

	private final UserAuthenticationTokenValidator validator;
	
	private final UserValidator userValidator;
	
	protected UserAuthenticationTokenManager(UserAuthenticationTokenMapper mapper,
			UserAuthenticationTokenValidator validator, UserValidator userValidator) {
		super(mapper, validator, EntityType.USER_AUTHENTICATION_TOKEN);

		this.mapper = mapper;
		this.validator = validator;
		this.userValidator = userValidator;
	}
	
	public UserAuthenticationToken getByToken(String token) {
		return mapper.getByToken(token);
	}
	
	/** Generates a new Authentication Token for the given user and device uuid
	 * 
	 * @param userId The user id
	 * @param deviceUuid The device uuid
	 * @return The created Token
	 * @throws EntityValidationException If a validation error occured
	 */
	
	public UserAuthenticationToken generateToken(long userId, String deviceUuid) throws EntityValidationException {
		UserAuthenticationToken token = new UserAuthenticationToken();
		
		token.setActive(true);
		token.setUserId(userId);
		token.setDeviceUuid(deviceUuid);
		token.setToken(generateRandomTokenValue());
		
		return create(token);
	}
	
	/** Generates a Random Token Value
	 *
	 * 	Token value is a random UUID
	 * 
	 * @return The random token value
	 */
	protected String generateRandomTokenValue() {
		return UUID.randomUUID().toString();
	}

	@Override
	protected void createValidationLogic(UserAuthenticationToken toCreate) throws EntityValidationException {
		//check that the user exists
		userValidator.exists(toCreate.getUserId());		
	}

	@Override
	protected void updateValidationLogic(UserAuthenticationToken toUpdate) throws EntityValidationException {
		//check that the user exists
		userValidator.exists(toUpdate.getUserId());		
	}

}
