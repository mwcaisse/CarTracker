package com.ricex.cartracker.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.ricex.cartracker.common.viewmodel.auth.UserViewModel;
import com.ricex.cartracker.common.viewmodel.auth.UserViewModelImpl;

public class JsonUserViewModelAdapter implements JsonDeserializer<UserViewModel>  {

	public UserViewModel deserialize(JsonElement json, Type typeOfT,	JsonDeserializationContext context) 
			throws JsonParseException {
		return context.deserialize(json, UserViewModelImpl.class);
	}
	
}
