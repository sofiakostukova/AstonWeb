package org.example.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.example.models.Book;

import java.lang.reflect.Type;

public class BookSerializer implements JsonSerializer<Book> {
    @Override
    public JsonElement serialize(Book book, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", book.getId());
        jsonObject.addProperty("title", book.getTitle());
        jsonObject.addProperty("publicationYear", book.getPublicationYear());

        if (book.getAuthor() != null) {
            jsonObject.addProperty("author_id", book.getAuthor().getId());
        } else {
            jsonObject.addProperty("author_id", (Number) null);;
        }
        jsonObject.addProperty("price", book.getPrice());

        return jsonObject;
    }
}