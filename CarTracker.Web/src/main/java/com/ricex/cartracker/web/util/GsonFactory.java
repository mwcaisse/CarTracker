package com.ricex.cartracker.web.util;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricex.cartracker.common.entity.TripStatus;
import com.ricex.cartracker.common.entity.User;
import com.ricex.cartracker.common.util.JsonDateMillisecondsEpochDeserializer;
import com.ricex.cartracker.common.util.JsonTripStatusSerializer;
import com.ricex.cartracker.common.util.JsonUserAdapter;
import com.ricex.cartracker.common.util.JsonUserViewModelAdapter;
import com.ricex.cartracker.common.viewmodel.UserViewModel;

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
				.registerTypeAdapter(User.class, new JsonUserAdapter())
				.registerTypeAdapter(UserViewModel.class, new JsonUserViewModelAdapter())
				.create();		
		return gson;
	}
	
}
