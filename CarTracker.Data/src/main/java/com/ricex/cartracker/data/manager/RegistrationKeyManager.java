package com.ricex.cartracker.data.manager;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.auth.RegistrationKey;
import com.ricex.cartracker.common.entity.auth.RegistrationKeyUse;
import com.ricex.cartracker.common.entity.auth.User;
import com.ricex.cartracker.data.mapper.auth.RegistrationKeyMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.RegistrationKeyValidator;

public class RegistrationKeyManager extends AbstractEntityManager<RegistrationKey> {

	private final RegistrationKeyMapper mapper;
	
	private final RegistrationKeyValidator validator;
	
	private final RegistrationKeyUseManager keyUseManager;
	
	public RegistrationKeyManager(RegistrationKeyMapper mapper, RegistrationKeyValidator validator, 
			RegistrationKeyUseManager keyUseManager) {
		super(mapper, validator, EntityType.REGISTRATION_KEY);

		this.mapper = mapper;
		this.validator = validator;
		this.keyUseManager = keyUseManager;
	}
	
	/** Checks if the Registration Key with the given key is valid
	 * 
	 * @param key The key to check
	 * @return True if the key is valid and able to be used, false otherwise
	 */
	public boolean isValidRegistrationKey(String key) {
		RegistrationKey regKey = getByKey(key);
		if (null != regKey) {
			return regKey.getUsesRemaining() > 0 && regKey.isActive();
		}
		return false;
	}
	
	/** Uses the given registration key
	 * 
	 * @param keyValue The key value of the key to use
	 * @param user The user to use the key for
	 */
	public void useRegistrationKey(String keyValue, User user) throws EntityValidationException {
		RegistrationKey key = getByKey(keyValue);
		if (null == key) {
			throw new EntityValidationException("Key doesn't exist!");
		}
		if (!isValidRegistrationKey(keyValue)) {
			throw new EntityValidationException("Key cannot be used to register a user!");
		}

		//decrement the keys remaining uses
		key.setUsesRemaining(key.getUsesRemaining() - 1);		
		update(key);
		
		//add the key use record
		RegistrationKeyUse keyUse = new RegistrationKeyUse();
		keyUse.setKeyId(key.getId());
		keyUse.setUserId(user.getId());
		keyUseManager.create(keyUse);
	}
	
	/** Gets the registration key by its key value
	 * 
	 * @param key
	 * @return
	 */
	public RegistrationKey getByKey(String key) {
		return mapper.getByKey(key);
	}

	@Override
	protected void createValidationLogic(RegistrationKey toCreate) throws EntityValidationException {
		if (null != getByKey(toCreate.getKey())) {
			throw new EntityValidationException(MessageFormat.format("A {0} already exists with the given key value!", entityType.getName())); 
		}
	}

	@Override
	protected void updateValidationLogic(RegistrationKey toUpdate) throws EntityValidationException {
		RegistrationKey oldKey = get(toUpdate.getId());
		if (!StringUtils.equalsIgnoreCase(toUpdate.getKey(), oldKey.getKey())) {
			throw new EntityValidationException("You cannot change the key value!");
		}
	}

}
