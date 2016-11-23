package com.ricex.cartracker.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ricex.cartracker.common.entity.auth.User;
import com.ricex.cartracker.common.viewmodel.auth.UserViewModel;
import com.ricex.cartracker.common.viewmodel.auth.UserViewModelImpl;

public class JsonUserAdapter  implements JsonSerializer<User> {

	public JsonElement serialize(User source, Type type, JsonSerializationContext context) {
		return context.serialize(source.toViewModel());
	}

}
