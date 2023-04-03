package com.example.geoguesserjava.entity.user;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import android.util.Base64;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonElement = jsonObject.get("image");
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
            // handle string type
            User user = new User();
            user.setId(jsonObject.get("id").getAsLong());
            user.setEmail(jsonObject.get("email").getAsString());
            user.setLevel(jsonObject.get("level").getAsInt());
            user.setPoints(jsonObject.get("points").getAsDouble());
            user.setFirstName(jsonObject.get("firstName").getAsString());
            user.setLastName(jsonObject.get("lastName").getAsString());
            user.setUsername(jsonObject.get("username").getAsString());
            user.setRegistrationDate(jsonObject.get("registrationDate").getAsString());
            Charset charset = StandardCharsets.UTF_8;

            byte[] byteArrray = Base64.decode(jsonObject.get("image").getAsString(),Base64.DEFAULT);
                user.setImage(byteArrray);

            return user;
        } else if (jsonElement.isJsonNull() || jsonElement.isJsonArray()) {
            // handle byte array type
            return new Gson().fromJson(json, User.class);
        } else {
            throw new JsonParseException("Invalid image type for User");
        }
    }
}