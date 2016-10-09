package com.ricex.cartracker.web.util;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.cartracker.common.entity.TripStatus;
import com.ricex.cartracker.common.util.JsonDateMillisecondsEpochDeserializer;
import com.ricex.cartracker.common.util.JsonTripStatusSerializer;

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
				.registerTypeAdapter(Date.class, new JsonDateMillisecondsEpochDeserializer())
				.registerTypeAdapter(TripStatus.class, new JsonTripStatusSerializer())
				.create();		
		return gson;
	}
	
}
