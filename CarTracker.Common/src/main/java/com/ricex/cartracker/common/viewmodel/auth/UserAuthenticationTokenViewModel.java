package com.ricex.cartracker.common.viewmodel.auth;

import java.io.Serializable;
import java.util.Date;

public class UserAuthenticationTokenViewModel implements Serializable {

	private long id;
	
	private long userId;
	
	private UserViewModel user;
	
	private String deviceUuid;
	
	private boolean active;
	
	private Date lastLogin;
	
	private String lastLoginAddress;
	
	private Date expirationDate;
	
	private Date createDate;
	
		
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
		this.user = user;
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

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}	
	
	
	
}
