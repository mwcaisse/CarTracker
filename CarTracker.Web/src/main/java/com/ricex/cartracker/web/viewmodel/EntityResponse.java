package com.ricex.cartracker.web.viewmodel;

import org.apache.commons.lang3.StringUtils;

public class EntityResponse<T> {

	private T data;
	
	private String errorMessage;
	
	public EntityResponse(T data) {
		this.data = data;
	}
	
	public EntityResponse(T data, String message) {
		this.data = data;
		this.errorMessage = message;
	}
	
	public boolean isValid() {
		return StringUtils.isBlank(errorMessage);
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
	
}