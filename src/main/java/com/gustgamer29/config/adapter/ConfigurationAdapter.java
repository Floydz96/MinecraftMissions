package com.gustgamer29.config.adapter;

import com.google.gson.JsonElement;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface ConfigurationAdapter {

    JavaPlugin getPlugin();

    void reload();

    void save(JsonElement element);

    void remove(JsonElement element);

    String getString(String path, String def);

    int getInteger(String path, int def);

    boolean getBoolean(String path, boolean def);

    List<String> getStringList(String path, List<String> def);

    List<String> getKeys(String path, List<String> def);

    Map<String, String> getStringMap(String path, Map<String, String> def);

    JsonElement getElement(String path);
}
