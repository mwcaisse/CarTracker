package com.ricex.cartracker.androidrequester.request;

import org.springframework.http.HttpStatus;

public class RequestResponse<T> {
	
	/** The successful response from the server */
	private final T response;
	
	/** The error response from the server */
	private final String error;
	
	/** The HttpStatus code of the response */
	private final HttpStatus statusCode;

	/** Whether or not this response is valid */
	private final boolean valid;
	
	/** Creates a new instance of RequestResponse, representing a successful response
	 * 
	 * @param response The parsed response received from the server
	 * @param statusCode The status code of the response
	 */
	public RequestResponse(T response, HttpStatus statusCode) {
		this.response = response;
		this.statusCode = statusCode;
		this.error = null;
		
		valid = true;
	}
	
	/** Creates a new instance of RequestResponse, representing an invalid (error) response
	 * 
	 * @param response The response (if any) received from the server
	 * @param error The error message received from the server
	 * @param statusCode The status code of the response
	 */
	public RequestResponse(T response, String error, HttpStatus statusCode) {
		this.response = response;
		this.error = error;
		this.statusCode = statusCode;
		
		valid = false;
	}
	
	
	/** Whether or not this response is valid
	 * 
	 * @return True if the server returned Http OK (200), otherwise false
	 */
	public boolean isValid() {
		return valid;
	}

	/** The response from the server if valid
	 * 
	 * @return The response from the server
	 */
	public T getResponse() {
		return response;
	}
	
	/** Returns the error received by the server, if invalid response
	 * 
	 * @return The error the server returned
	 */
	public String getError() {
		return error;
	}
	
	/** Return the status code received from the server
	 * 
	 * @return the status code received from the server
	 */
	public HttpStatus getStatusCode() {
		return statusCode;
	}	
	
	

}
