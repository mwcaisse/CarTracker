package com.ricex.cartracker.androidrequester.request.exception;


/** Thrown in response to a BAD_REQUEST status, meaning the request was invalid
 * 
 * @author Mitchell Caisse
 *
 */

public class InvalidRequestException extends RequestException {	

	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String error) {
		super(error);
	}
	
	public InvalidRequestException(String error, Exception cause) {
		super(error, cause);
	}

}
