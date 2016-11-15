package com.ricex.cartracker.data.validation;

public class EntityValidationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public EntityValidationException(String message) {
		super(message);
	}
	
	public EntityValidationException(String message, Throwable parent) {
		super(message, parent);
	}

}
