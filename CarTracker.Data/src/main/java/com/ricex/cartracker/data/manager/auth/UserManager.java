package com.ricex.cartracker.data.manager.auth;

import com.ricex.cartracker.common.entity.auth.User;
import com.ricex.cartracker.common.viewmodel.auth.UserRegistration;
import com.ricex.cartracker.data.manager.AbstractEntityManager;
import com.ricex.cartracker.data.mapper.auth.UserMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.util.PasswordHasher;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.auth.UserValidator;

public class UserManager extends AbstractEntityManager<User> {

	private final UserMapper mapper;
	
	private final UserValidator validator;
	
	private final PasswordHasher passwordHasher;
	
	private final RegistrationKeyManager registrationKeyManager;
	
	public UserManager(UserMapper mapper, UserValidator validator, PasswordHasher passwordHasher, 
			RegistrationKeyManager registrationKeyManager) {
		super(mapper, validator, EntityType.USER);
		
		this.mapper = mapper;
		this.validator = validator;
		this.passwordHasher = passwordHasher;
		this.registrationKeyManager = registrationKeyManager;
	}
	
	/** Gets a user by their username
	 * 
	 * @param username The user's username
	 * @return THe user with the given username, or null if none exists
	 */
	public User getByUsername(String username) {
		return mapper.getByUsername(username);
	}
	
	/** Determines whether the given user name is available or not
	 * 
	 * @param username The username to check
	 * @return True if is available, false otherwise
	 */
	public boolean isUsernameAvailable(String username) {
		return null == getByUsername(username);
	}
	
	/** Registers the given user
	 * 
	 * @param registration
	 * @return
	 * @throws EntityValidationException
	 */
	
	public User register(UserRegistration registration) throws EntityValidationException {
		if (!registrationKeyManager.isValidRegistrationKey(registration.getRegistrationKey())) {
			throw new EntityValidationException("Registration key provided is not valid!");
		}
		
		User user = new User();			
		user.setUsername(registration.getUsername());
		user.setEmail(registration.getEmail());
		user.setPassword(passwordHasher.hashPassword(registration.getPassword()));
		user.setName(registration.getName());		
		user.setActive(true);
		user.setLocked(false);		
		user = create(user);
		
		try {
			registrationKeyManager.useRegistrationKey(registration.getRegistrationKey(), user);
		}
		catch (EntityValidationException e) {
			//error occurred while consuming the registration key
			//delete our user
			delete(user.getId());
			
			throw new EntityValidationException("Could not consume the registration key! User not registered.", e);
		}
		
		return user;
	}

	@Override
	protected void createValidationLogic(User toCreate) throws EntityValidationException {
		if (!isUsernameAvailable(toCreate.getUsername())) {
			throw new EntityValidationException("A user with this username already exists!");
		}
	}

	@Override
	protected void updateValidationLogic(User toUpdate) throws EntityValidationException {
			
	}

}
