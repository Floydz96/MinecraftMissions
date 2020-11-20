package com.gustgamer29.config.serializer;

import com.google.gson.Gson;

public interface JsonSerializer<E> {

    E deserialize(Gson gson, String json);

    String serialize(E serialize, Gson gson);

}
