package com.ricex.cartracker.androidrequester.request;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.cartracker.androidrequester.request.exception.InvalidRequestException;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.exception.UnauthenticationRequestException;
import com.ricex.cartracker.androidrequester.request.exception.UnauthorizedRequestException;
import com.ricex.cartracker.androidrequester.request.user.LoginTokenRequest;
import com.ricex.cartracker.common.auth.TokenAuthentication;
import com.ricex.cartracker.common.entity.TripStatus;
import com.ricex.cartracker.common.util.JsonDateMillisecondsEpochDeserializer;
import com.ricex.cartracker.common.util.JsonTripStatusSerializer;
import com.ricex.cartracker.common.viewmodel.EntityResponse;

import android.os.AsyncTask;
import android.util.Log;

/** Abstract Request implementing the Request interface. 
 * 
 * @author Mitchell Caisse
 *
 * @param <T> The type of the expected result of the request
 */

public abstract class AbstractRequest<T> implements Request<T> {	
	
	/** The rest template */
	protected RestTemplate restTemplate;
	
	/** The server address */
	protected final String serverAddress;
	
	/** The security context to use */
	private SessionContext sessionContext;
	
	/** The application preferences */
	protected ApplicationPreferences applicationPreferences;
	
	/** Creates a new instance of AbstractRequest. Initializes the required fields
	 * 
	 */
	
	public AbstractRequest(ApplicationPreferences applicationPreferences) {
		this.applicationPreferences = applicationPreferences;
		this.serverAddress = processServerAddress();		
		this.sessionContext = SessionContext.INSTANCE;	
		restTemplate = new RestTemplate();		
		
		//Create the gson object to decode Json messages
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG)
				.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
				.registerTypeAdapter(TripStatus.class, new JsonTripStatusSerializer())
				.create();
		
		//create the Gson message converter for spring, and set its Gson
		GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
		converter.setGson(gson);
		
		//add the gson message converter to the rest template
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
	}
	
	/** Executes the request synchronously and returns the results of the request
	 * 
	 * @return The results of the request
	 * @throws RequestException When an issue occurred while executing the request
	 */
	
	public T execute() throws RequestException {
		RequestResponse<T> results = executeRequest();
		return processResponse(results);
	}
	
	/** Executes the request synchronously and calls the callback with the results or error
	 * 
	 * @param callback The callback to call when the request is finished executing
	 */
	
	@Override
	public void executeAsync(final RequestCallback<T> callback) {
		new AsyncTask<Void, Void, RequestResponse<T>>() {

			@Override
			protected RequestResponse<T> doInBackground(Void... params) {
				RequestResponse<T> response = null;
				try {
					response = executeRequest();
				}
				catch (Exception e) {
					callback.onError(e);
				}	
				return response;
			}			
			
			@Override
			protected void onPostExecute(RequestResponse<T> results) {
				//if results are null, then error callback has been called already
				try {
					T resEntity = processResponse(results);
					callback.onSuccess(resEntity);
				}
				catch (RequestException e) {
					callback.onFailure(e, results);
				}		
			}
			
		}.execute();
	}
	
	
	/** Returns the UID for the device this app is running on
	 * 
	 * @return the UID, or -1 if failed.
	 */
	
	protected String getDeviceUID() {		
		return applicationPreferences.getDeviceUID();
	}
	
	/** Executes the request 
	 * 
	 * @return The AFTResponse representing the results of the request
	 * @throws RequestException If an error occurred while making the request
	 */
	protected abstract RequestResponse<T> executeRequest() throws RequestException;
	
	/** Performs a get to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 * @throws RequestException If an error occurred while making the request
	 */
	protected RequestResponse<T> getForObject(String url, ParameterizedTypeReference<EntityResponse<T>> responseType, Object... urlVariables) throws RequestException {
		return makeRequest(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, urlVariables);
	}
	
	/** Performs a post to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param requestBody The body of the request
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 * @throws RequestException If an error occurred while making the request
	 */
	
	protected RequestResponse<T> postForObject(String url, Object requestBody, ParameterizedTypeReference<EntityResponse<T>> responseType, Object... urlVariables) throws RequestException {
		return makeRequest(url, HttpMethod.POST, new HttpEntity<Object>(requestBody), responseType, urlVariables);
	}
	
	/** Performs a put to the specified url, and returns the results as the specified type
	 * 
	 * @param url The url to make the request to
	 * @param requestBody The body of the request
	 * @param responseType The expected response
	 * @param urlVariables The url parameters
	 * @return The results of the request, or null if there was an error
	 * @throws RequestException If an error occurred while making the request
	 */
	
	protected RequestResponse<T> putForObject(String url, Object requestBody, ParameterizedTypeReference<EntityResponse<T>> responseType, Object... urlVariables) throws RequestException {
		return makeRequest(url, HttpMethod.PUT, new HttpEntity<Object>(requestBody), responseType, urlVariables);
	}
	
	/** Makes a generic request to the server with the specified attributes
	 * 
	 *  Automatically adds and requests the authentication to each request. Will automatically retry the request if the
	 *  	Authentication token has expired	
	 *  
	 * @param url The URL to make the request on
	 * @param method The HTTP method of the request
	 * @param requestEntity The request Entity representing the request body and headers
	 * @param responseType The expected response type of the request
	 * @param urlVariables Any url parameters
	 * @return The results from the request, or null if there were any errors
	 * @throws RequestException If an error occurred while making the request
	 */	
	
	protected RequestResponse<T> makeRequest(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<EntityResponse<T>> responseType, Object... urlVariables) throws RequestException {	
		url = processUrl(url);
		HttpEntity<?> entity = requestEntity;

		//Only add the authentication headers if we are not making a login request
		if (!isLoginUrl(url)) {
			entity = addAuthenticationHeaders(requestEntity);
		}
		try {
			ResponseEntity<EntityResponse<T>> results = restTemplate.exchange(url, method, entity, responseType, urlVariables);
			return processSucessfulRequestResponse(results, url, method, requestEntity, responseType, urlVariables);
		}
		catch (HttpStatusCodeException e) {
			String responseBody = e.getResponseBodyAsString();
			HttpStatus status = e.getStatusCode();
			return processErrorRequestResponse(responseBody, status, url, method, requestEntity, responseType, urlVariables);
		}
	}

	protected String getTokenLoginAddress() {
		return serverAddress + "user/login/token";
	}

	protected String getPasswordLoginAddress() {
		return serverAddress + "user/login/password";
	}

	protected boolean isLoginUrl(String url) {
		return getTokenLoginAddress().equalsIgnoreCase(url) || getPasswordLoginAddress().equalsIgnoreCase(url);
	}
	
	/** Adds the appropriate Authentication headers to the given HttpEntity. The entity passed in is not modified. New entity
	 * 		is returned.
	 * 
	 * @param entity The HttpEntity to add the request headers
	 * @return The new HttpEntity with the updated Authentication Headers
	 */
	private HttpEntity<?> addAuthenticationHeaders(final HttpEntity<?> entity) {
		//add the session token header
		//add it regardless if we have a valid authentication token or not. We want to hit the
		//	auth token filter to get the 401 response
		HttpHeaders headers = new HttpHeaders();
		headers.putAll(entity.getHeaders());
		headers.add(TokenAuthentication.SESSION_TOKEN_HEADER, sessionContext.getSessionToken());

		return new HttpEntity<Object>(entity.getBody(), headers);

	}
	
	/** Processes the results of an AFT Request that returned a successful http status code (200 OK)
	 * 
	 * @param responseEntity The results of the request 
	 * @param url The url of the request
	 * @param method The method of the request
	 * @param requestEntity The response entity of the request
	 * @param responseType the response type of the request
	 * @param urlVariables The url variables of the request
	 * @return The processed results of the request, the request body or null if there was an error
	 */
	private RequestResponse<T> processSucessfulRequestResponse(ResponseEntity<EntityResponse<T>> responseEntity, String url, HttpMethod method, HttpEntity<?> requestEntity, 
			ParameterizedTypeReference<EntityResponse<T>> responseType, Object... urlVariables) throws RequestException {		
		RequestResponse<T> response = new RequestResponse<T>(responseEntity.getBody(), responseEntity.getStatusCode());		
	
		//if the code wasn't UNAUTHORIZED, and we need a session token, extract it from the response header
		if (sessionContext.needSessionToken()) {
			String sessionToken = extractSessionToken(responseEntity);
			if (StringUtils.isEmpty(sessionToken)) {
				//ignore for now, we are not using session tokens
				//throw new RequestException("Unable to retreive the session token from our request!");
			}
			sessionContext.setSessionToken(sessionToken);

		}
		return response;
	}
	
	/** Processes the results of an AFT Request that returned a non-successful status code (HTTP 400 or 500 )
	 * 
	 * @param responseBody The body of the response returned
	 * @param url The url of the request
	 * @param method The method of the request
	 * @param requestEntity The response entity of the request
	 * @param responseType the response type of the request
	 * @param urlVariables The url variables of the request
	 * @return The processed results of the request, the request body or null if there was an error
	 */
	private RequestResponse<T> processErrorRequestResponse(String responseBody, HttpStatus status, String url, HttpMethod method, 
			HttpEntity<?> requestEntity, ParameterizedTypeReference<EntityResponse<T>> responseType, Object... urlVariables) throws RequestException {
		
		RequestResponse<T> response = new RequestResponse<T>(null, responseBody, status);		



		if (status == HttpStatus.UNAUTHORIZED) {
			//401 was returned, prompt the user to login
			sessionContext.invalidateSessionToken(); //invalidate the current token, if any
			String authToken = applicationPreferences.getAuthToken();
			if (StringUtils.isEmpty(authToken)) {
				//we don't have an authtoken. raise this up
				throw new UnauthenticationRequestException("Unable to make request, not authenticated with the server and no credentials");
			}
			boolean res = new LoginTokenRequest(applicationPreferences, authToken).execute();
			
			if (res) {
				return executeRequest();
			}
			else {
				throw new UnauthenticationRequestException("Unable to make request, not authenticated, and could not obtain session token!");
			}
			
		}	
		return response;
	}
	
	/** Processes the Response received after making a request.
	 * 
	 *  If the response is valid (server returned Http OK) then the object the server responded with is returned. Otherwise an exception
	 *  	is thrown indicating the error that occurred.
	 * 
	 * @param response The response received from the server
	 * @return The parsed response object
	 * @throws RequestException If response is invalid, the error returned by the server
	 */
	private T processResponse(RequestResponse<T> response) throws RequestException {
		if (response.isValid()) {
			return response.getData();
		}
		else if (response.isValidServerResponse()) {
			throw new InvalidRequestException(response.getError());
		}
		else {
			String error = response.getError();
			Log.w("CT_AR", "process response, error occured: " + error);
			switch (response.getStatusCode()) {
			case BAD_REQUEST:
				throw new InvalidRequestException(error);			
			case FORBIDDEN:
				throw new UnauthorizedRequestException(error);		
			default:
				throw new RequestException(error);
			}
		}
	}
	
	/** Extracts the session token from the http response
	 * 
	 * @param entity The http response to extract the token from
	 * @return The session token
	 */
	
	private String extractSessionToken(ResponseEntity<?> entity) {
		String token = entity.getHeaders().getFirst(TokenAuthentication.SESSION_TOKEN_HEADER);
		//set it as the authorization token, if it was found, and is not empty
		if (StringUtils.isNotBlank(token)) {
			return token;
		}
		return null;
	}
	
	
	/** Processes the given URL
	 * 
	 * 		Checks if the URL given is a relative URL or an absolute URL.
	 * 		If it is a relative URL adds prepends the server address, otherwise leaves it alone
	 * 
	 * @param url
	 * @return
	 */
	private String processUrl(String url) {
		if (null == url) {
			return new String();
		}
		if (url.toLowerCase().startsWith("http://") ||
							 url.toLowerCase().startsWith("https://")) {
			return url;
		}
		url = StringUtils.stripStart(url,  "/");
		return serverAddress + url;				
	}
	
	/** Processes the server address to remove any potential issues also adds the api suffix
	 * 
	 * @return The processed server address
	 */
	private String processServerAddress() {
		String serverAddress = applicationPreferences.getServerAddress();
		
		//if the server address doesn't start with either http or https, add in http
		if (!(StringUtils.startsWithIgnoreCase(serverAddress, "http://") ||
			  StringUtils.startsWithIgnoreCase(serverAddress, "https://"))) {
			serverAddress = "http://" + serverAddress;
		}
		
		//strip any /'s from the end of the url				
		serverAddress = StringUtils.stripEnd(serverAddress, "/");
		
		//add the api suffix
		serverAddress += "/api/";
		return serverAddress;
	}
	
	
}
