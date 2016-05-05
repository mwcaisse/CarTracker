package com.ricex.cartracker.androidrequester.request.exception;

/** Exception thrown when the server response is valid, but the entity response is invalid
 * 
 * @author Mitchell Caisse
 *
 */

public class InvalidEntityException extends RequestException  {

	private static final long serialVersionUID = 1L;

	public InvalidEntityException(String error) {
		super(error);
	}
	
	public InvalidEntityException(String error, Exception cause) {
		super(error, cause);
	}

}
