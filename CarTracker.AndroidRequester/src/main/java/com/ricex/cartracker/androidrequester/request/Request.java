/**
 * 
 */
package com.ricex.cartracker.androidrequester.request;

import com.ricex.cartracker.androidrequester.request.exception.RequestException;

/**
 * @author Mitchell Caisse
 *
 */
public interface Request<T> {

	/** Executes the request synchronously and returns the results of the request
	 * 
	 * @return The results of the request
	 * @throws RequestException When an issue occurred while executing the request
	 */
	public T execute() throws RequestException;
	
	
	/** Executes the request synchronously and calls the callback with the results or error
	 * 
	 * @param callback The callback to call when the request is finished executing
	 */
	public void executeAsync(RequestCallback<T> callback);
}
