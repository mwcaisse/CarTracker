package com.ricex.cartracker.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;

public class JsonUserAuthenticationTokenAdapter implements JsonSerializer<UserAuthenticationToken> {
	
	public JsonElement serialize(UserAuthenticationToken source, Type type, JsonSerializationContext context) {
		return context.serialize(source.toViewModel());
	}
	
}
