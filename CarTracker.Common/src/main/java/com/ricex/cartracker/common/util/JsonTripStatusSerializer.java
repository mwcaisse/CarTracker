package com.ricex.cartracker.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ricex.cartracker.common.entity.TripStatus;

public class JsonTripStatusSerializer implements JsonSerializer<TripStatus>, JsonDeserializer<TripStatus> {

	@Override
	public TripStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		TripStatus status = TripStatus.fromString(json.getAsString());
		if (null == status) {
			status = TripStatus.valueOf(json.getAsString());
		}
		return status;
	}

	@Override
	public JsonElement serialize(TripStatus src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.toString());
	}
	

}
