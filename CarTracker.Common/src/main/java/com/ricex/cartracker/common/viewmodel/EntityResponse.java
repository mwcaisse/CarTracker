package com.ricex.cartracker.common.viewmodel;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class EntityResponse<T> implements Serializable {

	private T data;
	
	private String errorMessage;
	
	private final boolean valid;
	
	public EntityResponse(T data) {
		this.data = data;
		this.valid = true;
	}
	
	public EntityResponse(T data, String message) {
		this.data = data;
		this.errorMessage = message;
		this.valid = StringUtils.isBlank(errorMessage);
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}	
	
	public boolean isValid() {
		return valid;
	}
	
}
