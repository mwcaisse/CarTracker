package com.ricex.cartracker.androidrequester.request;

import org.springframework.http.HttpStatus;

import com.ricex.cartracker.common.viewmodel.EntityResponse;

public class RequestResponse<T> {
	
	/** The successful response from the server */
	private final EntityResponse<T> response;	

	/** The error response from the server */
	private final String error;
	
	/** The HttpStatus code of the response */
	private final HttpStatus statusCode;
	
	/** Creates a new instance of RequestResponse, representing a successful response
	 * 
	 * @param response The parsed response received from the server
	 * @param statusCode The status code of the response
	 */
	public RequestResponse(EntityResponse<T> response, HttpStatus statusCode) {
		this.response = response;
		this.statusCode = statusCode;	
		this.error = null;	
	}
	
	/** Creates a new instance of RequestResponse, representing an invalid (error) response
	 * 
	 * @param response The response (if any) received from the server
	 * @param error The error message received from the server
	 * @param statusCode The status code of the response
	 */
	public RequestResponse(EntityResponse<T> response, String error, HttpStatus statusCode) {
		this.response = response;
		this.error = error;
		this.statusCode = statusCode;
	}

	/** The response from the server if valid
	 * 
	 * @return The response from the server
	 */
	public T getData() {
		if (null == response) {
			return null;
		}
		return response.getData();
	}
	
	/** Returns the error received by the server, if invalid response
	 * 
	 * @return The error the server returned
	 */
	public String getError() {
		if (isValidServerResponse() && null != response) {
			return response.getErrorMessage();
		}
		return error;
	}
	
	/** Return the status code received from the server
	 * 
	 * @return the status code received from the server
	 */
	public HttpStatus getStatusCode() {
		return statusCode;
	}	
	
	/** Returns whether the server response was okay or not
	 * 
	 * @return True if server responded without errors
	 */
	public boolean isValidServerResponse() {
		return HttpStatus.OK.equals(getStatusCode());
	}
	
	/** Returns whether this response is valid or not
	 * 
	 * 	If this is a valid server response, checks that Entity Response is valid.
	 * 		-If Entity Response is valid returns true
	 * 		-If Entity Response is invalid or null returns false
	 * 
	 * 	If in valid server response returns false
	 * 
	 * @return True if response is valid, false otherwise
	 */
	public boolean isValid() {
		if (isValidServerResponse()) {
			return response != null && response.isValid();
		}
		return false;
	}
	
	

}
