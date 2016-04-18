package com.ricex.cartracker.androidrequester.request.exception;


/** Thrown when a user does not have the Authorization/privileges to perform the requested action
 * 
 * @author Mitchell Caisse
 *
 */
public class UnauthorizedRequestException extends RequestException {
	
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedRequestException(String error) {
		super(error);
	}
	
	public UnauthorizedRequestException(String error, Exception cause) {
		super(error, cause);
	}
}
