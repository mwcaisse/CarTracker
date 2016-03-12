package com.ricex.cartracker.web.util;

import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** Factory Object for creating the Gson Parser
 * 
 * @author Mitchell Caisse
 *
 */

public class GsonFactory {

	public GsonFactory() {
		
	}
	
	/** Factory method to construct the Gson parser
	 * 
	 * @return The Gson parser
	 */
	public Gson constructGson() {
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat(DateFormat.LONG)
				.create();		
		return gson;
	}
	
}
