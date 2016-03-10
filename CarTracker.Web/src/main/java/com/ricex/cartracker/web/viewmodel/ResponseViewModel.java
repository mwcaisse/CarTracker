package com.ricex.cartracker.web.viewmodel;

import org.apache.commons.lang3.StringUtils;

public class ResponseViewModel<T> {

	private T data;
	
	private String errorMessage;
	
	public ResponseViewModel(T data) {
		this.data = data;
	}
	
	public ResponseViewModel(T data, String message) {
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
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
