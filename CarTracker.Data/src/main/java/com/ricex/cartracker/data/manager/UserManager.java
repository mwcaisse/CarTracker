package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.common.entity.User;
import com.ricex.cartracker.common.viewmodel.UserRegistration;
import com.ricex.cartracker.data.mapper.UserMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.util.PasswordHasher;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.UserValidator;

public class UserManager extends AbstractEntityManager<User> {

	private final UserMapper mapper;
	
	private final UserValidator validator;
	
	private PasswordHasher passwordHasher;
	
	protected UserManager(UserMapper mapper, UserValidator validator, PasswordHasher passwordHasher) {
		super(mapper, validator, EntityType.USER);
		
		this.mapper = mapper;
		this.validator = validator;
		this.passwordHasher = passwordHasher;
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
		User user = new User();	
		
		user.setUsername(registration.getUsername());
		user.setEmail(registration.getEmail());
		user.setPassword(passwordHasher.hashPassword(registration.getPassword()));
		
		user.setActive(true);
		user.setLocked(false);
		
		return create(user);
	}

	@Override
	protected void createValidationLogic(User toCreate) throws EntityValidationException {
		if (null != getByUsername(toCreate.getUsername())) {
			throw new EntityValidationException("A user with this username already exists!");
		}
	}

	@Override
	protected void updateValidationLogic(User toUpdate) throws EntityValidationException {
			
	}

}
