package com.gustgamer29.config.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {

    public static final Gson GSON_STATIC_PROVIDER = new GsonBuilder().disableHtmlEscaping().create();
    public static final Gson GSON_STATIC_WRITER = new GsonBuilder().setPrettyPrinting().create();

}
