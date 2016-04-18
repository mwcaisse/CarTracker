package com.ricex.cartracker.androidrequester.request.exception;

/** Thrown when a request is made, but the user is Unauthenticated
 * 
 * @author Mitchell Caisse
 *
 */
public class UnauthenticationRequestException extends RequestException {

	public UnauthenticationRequestException(String error) {
		super(error);
	}
	
	public UnauthenticationRequestException(String error, Exception cause) {
		super(error, cause);
	}

}
