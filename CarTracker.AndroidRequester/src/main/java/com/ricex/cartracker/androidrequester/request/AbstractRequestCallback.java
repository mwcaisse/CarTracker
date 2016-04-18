package com.ricex.cartracker.androidrequester.request;

import com.ricex.cartracker.androidrequester.request.exception.RequestException;


public abstract class AbstractRequestCallback<T> implements RequestCallback<T> {

	public void onSuccess(T results) {}

	public void onFailure(RequestException e, AFTResponse<T> response) {}

	public void onError(Exception e) {}

}
