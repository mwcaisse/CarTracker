package com.ricex.cartracker.common.entity.auth;

import java.util.List;

import com.ricex.cartracker.common.entity.AbstractEntity;

public class RegistrationKey extends AbstractEntity {

	private String key;
	
	private int usesRemaining;
	
	private boolean active;
	
	private List<RegistrationKeyUse> keyUses;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the usesRemaining
	 */
	public int getUsesRemaining() {
		return usesRemaining;
	}

	/**
	 * @param usesRemaining the usesRemaining to set
	 */
	public void setUsesRemaining(int usesRemaining) {
		this.usesRemaining = usesRemaining;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the keyUses
	 */
	public List<RegistrationKeyUse> getKeyUses() {
		return keyUses;
	}

	/**
	 * @param keyUses the keyUses to set
	 */
	public void setKeyUses(List<RegistrationKeyUse> keyUses) {
		this.keyUses = keyUses;
	}	
	
}
