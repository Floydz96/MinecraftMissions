package com.gustgamer29.config.json;

import com.google.gson.JsonArray;
import com.gustgamer29.config.adapter.ConfigurationAdapter;

public interface JsonKey<E> {

    int elementPosition();

    E get(ConfigurationAdapter adapter);
}