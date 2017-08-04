package com.ricex.cartracker.placesrequester.entity.placesearch;

import java.util.List;

public class PlaceSearchResult {

	private List<String> htmlAttributes;
	private List<PlaceSearchModel> results;
	private String status;
	
	/**
	 * @return the htmlAttributes
	 */
	public List<String> getHtmlAttributes() {
		return htmlAttributes;
	}
	
	/**
	 * @param htmlAttributes the htmlAttributes to set
	 */
	public void setHtmlAttributes(List<String> htmlAttributes) {
		this.htmlAttributes = htmlAttributes;
	}
	
	/**
	 * @return the results
	 */
	public List<PlaceSearchModel> getResults() {
		return results;
	}
	
	/**
	 * @param results the results to set
	 */
	public void setResults(List<PlaceSearchModel> results) {
		this.results = results;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
