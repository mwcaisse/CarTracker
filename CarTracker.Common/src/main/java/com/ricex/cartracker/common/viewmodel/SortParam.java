package com.ricex.cartracker.common.viewmodel;

import java.io.Serializable;

public class SortParam implements Serializable {

	private String propertyId;
	
	private boolean ascending;

	public SortParam() {
		ascending = true;
	}
	
	/**
	 * @return the propertyId
	 */
	public String getPropertyId() {
		return propertyId;
	}

	/**
	 * @param propertyId the propertyId to set
	 */
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	/**
	 * @return the ascending
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * @param ascending the ascending to set
	 */
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}	
	
}
