package com.ricex.cartracker.placesrequester;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.ricex.cartracker.placesrequester.entity.placesearch.PlaceSearchModel;
import com.ricex.cartracker.placesrequester.entity.placesearch.PlaceSearchResult;

public class PlaceRequester {

	private static Logger log = LoggerFactory.getLogger(PlaceRequester.class);
	
	private RestTemplate template;
	
	private String apiKey;
	
	public PlaceRequester(String apiKey) {
		this (new RestTemplate(), apiKey);
	}
	
	public PlaceRequester(RestTemplate template, String apiKey) {
		this.template = template;
		this.apiKey = apiKey;
	}
	
	public List<PlaceSearchModel> getPlacesNearby(double latitude, double longitude, int range) {
		String url = MessageFormat.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={0},{1}&radius={2}&key={3}", 
				latitude, longitude, range, apiKey);		
		PlaceSearchResult result = template.getForObject(url, PlaceSearchResult.class);	
		return result.getResults();
	}
	
}
