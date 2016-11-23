package com.ricex.cartracker.common.entity.auth;

import java.util.Date;

import com.ricex.cartracker.common.entity.AbstractEntity;
import com.ricex.cartracker.common.viewmodel.auth.UserAuthenticationTokenViewModel;

public class UserAuthenticationToken extends AbstractEntity {

	private long userId;
	
	private User user;
	
	private String token;
	
	private String deviceUuid;
	
	private boolean active;
	
	private Date lastLogin;
	
	private String lastLoginAddress;
	
	private Date expirationDate;

	public UserAuthenticationToken() {
		userId = INVALID_ID;
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
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the deviceUuid
	 */
	public String getDeviceUuid() {
		return deviceUuid;
	}

	/**
	 * @param deviceUuid the deviceUuid to set
	 */
	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
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
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the lastLoginAddress
	 */
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}

	/**
	 * @param lastLoginAddress the lastLoginAddress to set
	 */
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
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
	
	public boolean isValid() {
		return isActive() &&
				(null == expirationDate || new Date().before(expirationDate));
	}
	
	public UserAuthenticationTokenViewModel toViewModel() {
		UserAuthenticationTokenViewModel model = new UserAuthenticationTokenViewModel();
		
		model.setId(id);
		model.setUserId(userId);
		model.setUser(user);
		model.setDeviceUuid(deviceUuid);
		model.setActive(active);
		model.setLastLogin(lastLogin);
		model.setLastLoginAddress(lastLoginAddress);
		model.setExpirationDate(expirationDate);
		model.setCreateDate(createDate);
		
		return model;
				
	}
	
}
