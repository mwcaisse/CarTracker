package com.ricex.cartracker.androidrequester.request.exception;


/** Exception thrown when invalid credentials are provided during a request
 * 
 * @author Mitchell Caisse
 *
 */

public class InvalidCredentialsException extends RequestException {

	private static final long serialVersionUID = -6223879697256967201L;

	/** Constructs a new InvalidCredentialsException with the given message
	 * 
	 * @param message The message describing the exception
	 */
	public InvalidCredentialsException(String message) {
		super(message);
	}
	
	/** Constructs a new InvalidCredentialsException with the given message and root cause
	 * 
	 * @param message The message describing the exception
	 * @param e The exception that caused this exception
	 */
	public InvalidCredentialsException(String message, Exception e) {
		super(message, e);
	}
	
}
