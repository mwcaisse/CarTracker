package com.ricex.cartracker.androidrequester.request.exception;

/** Thrown when a request is made, but the user is Unauthenticated
 * 
 * @author Mitchell Caisse
 *
 */
public class UnauthenticationRequestException extends RequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6990806863966090697L;

	public UnauthenticationRequestException(String error) {
		super(error);
	}
	
	public UnauthenticationRequestException(String error, Exception cause) {
		super(error, cause);
	}

}
