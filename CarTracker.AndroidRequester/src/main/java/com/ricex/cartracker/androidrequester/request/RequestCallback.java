package com.ricex.cartracker.androidrequester.request;

import com.ricex.cartracker.androidrequester.request.exception.RequestException;



/** The callback to use when making an asynchronous request
 * 
 * @author Mitchell Caisse
 *
 * @param <T> The type of the response excepted from the request
 */
public interface RequestCallback<T> {
	
	/** Called when the request completed successfully
	 * 
	 * @param results The results of the request
	 */
	
	public void onSuccess(T results);
	
	/** Called when the request completed with out error, but the server returned a non HTTP 200 result, indicating an invalid request
	 * 
	 * @param e The exception representing the error from the server
	 * @param response The response received from the server
	 */
	
	public void onFailure(RequestException e, RequestResponse<T> response);
	
	/** Called when an error / exception occurred while processing the request
	 * 
	 * @param e The exception that occurred
	 */
	public void onError(Exception e);
	
}
