package com.ricex.cartracker.common.entity.auth;

import java.util.Date;

import com.ricex.cartracker.common.entity.AbstractEntity;
import com.ricex.cartracker.common.viewmodel.auth.UserViewModel;
import com.ricex.cartracker.common.viewmodel.auth.UserViewModelImpl;

public class User extends AbstractEntity implements UserViewModel {

	private static final long serialVersionUID = 1L;

	private String username;	
	
	private String password;
	
	private String name;
	
	private String email;
	
	private boolean active;
	
	private boolean locked;
	
	private Date expirationDate;
	
	private Date passwordExpirationDate;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the passwordExpirationDate
	 */
	public Date getPasswordExpirationDate() {
		return passwordExpirationDate;
	}

	/**
	 * @param passwordExpirationDate the passwordExpirationDate to set
	 */
	public void setPasswordExpirationDate(Date passwordExpirationDate) {
		this.passwordExpirationDate = passwordExpirationDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public UserViewModelImpl toViewModel() {
		UserViewModelImpl viewModel = new UserViewModelImpl();	
		
		viewModel.setId(getId());
		viewModel.setName(getName());
		viewModel.setUsername(getUsername());
		
		return viewModel;
	}

	
	
}
