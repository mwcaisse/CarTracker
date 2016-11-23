package com.ricex.cartracker.common.entity.auth;

import com.ricex.cartracker.common.entity.AbstractEntity;
import com.ricex.cartracker.common.viewmodel.auth.UserViewModel;

public class RegistrationKeyUse extends AbstractEntity {

	private long keyId;
	
	private long userId;
	
	private UserViewModel user;

	public RegistrationKeyUse() {
		keyId = INVALID_ID;
		userId = INVALID_ID;
	}
	
	/**
	 * @return the keyId
	 */
	public long getKeyId() {
		return keyId;
	}

	/**
	 * @param keyId the keyId to set
	 */
	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the user
	 */
	public UserViewModel getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserViewModel user) {
		if (null != user) {
			userId = user.getId();
		}
		this.user = user;
	}
	
	
	
}
