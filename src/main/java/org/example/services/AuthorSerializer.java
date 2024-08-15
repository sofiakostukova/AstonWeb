package org.example.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.example.models.Author;

import java.lang.reflect.Type;

public class AuthorSerializer implements JsonSerializer<Author> {
    @Override
    public JsonElement serialize(Author author, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", author.getId());
        jsonObject.addProperty("name", author.getName());
        return jsonObject;
    }
}