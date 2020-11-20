package com.gustgamer29.config.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.gustgamer29.config.json.GsonProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GsonConfigurationAdapter implements ConfigurationAdapter {

//    private static final BiFunction<String, JsonObject, JsonElement> NESTED_ELEMENTS = (path, jobj) -> {
//
//        if (path == null || jobj == null || path.isEmpty())
//            return null;
//
//        String[] sPath = path.split("\\.");
//
//        if (sPath.length == 0 && jobj.has(path)) {
//            return jobj.get(path);
//        }
//
//        JsonElement element = null;
//
//        for (String s : jobj.keySet()) {
//            if (!s.equals(sPath[0]))
//                continue;
//
//            element = jobj.get(s);
//        }
//
//        if (element == null)
//            return jobj;
//
//        for (int i = 0; i < sPath.length; ++i) {
//            while (true) {
//                element = jobj.get(sPath[i]);
//
//                if (sPath.length == i + 1) {
//                    return element;
//                }
//
//                if (element.isJsonArray()) {
//                    //   elementIterator = element.getAsJsonArray().iterator();
//                    break;
//                }
//
//                if (element.isJsonObject() && element.getAsJsonObject().has(sPath[i + 1])) {
//                    return element.getAsJsonObject().get(sPath[i + 1]);
//                }
//
//                return element;
//            }
//        }
//
//        return element;
//    };

    private final JavaPlugin plugin;
    private final Path path;

    private transient JsonObject rootContent;
    private String rootSection;

    public GsonConfigurationAdapter(JavaPlugin plugin, Path path, String rootSection) {
        this.plugin = plugin;
        this.path = path;
        this.rootSection = rootSection;

        reload();
    }

    @Override
    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void save(JsonElement element) {
        try (BufferedWriter bw = Files.newBufferedWriter(this.path, StandardCharsets.UTF_8)) {
            Gson gson = GsonProvider.GSON_STATIC_WRITER;
            JsonWriter writer = new JsonWriter(bw);
            gson.toJson(element, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(JsonElement element) {
        System.out.println(element);
    }

    @Override
    public void reload() {

        if (!Files.exists(this.path)) {
            throw new RuntimeException("File " + this.path.getFileName() + " wasn't found.");
        }

        try (BufferedReader br = Files.newBufferedReader(this.path)) {

            Gson gson = GsonProvider.GSON_STATIC_PROVIDER;

            this.rootContent = gson.fromJson(br, JsonObject.class).get(rootSection).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private JsonElement resolvePath(String path){
        if (path == null || path.isEmpty())
            return null;

        String[] sPath = path.split("\\.");

        if (sPath.length == 0 && rootContent.has(path)) {
            return rootContent.get(path);
        }

        if (!rootContent.has(sPath[0]))
            return rootContent;

        JsonElement firstElement = rootContent.get(sPath[0]);

        if(firstElement.isJsonArray())
            return firstElement;

        JsonObject obj = firstElement.getAsJsonObject();
        JsonElement element = null;

        for(int i = 0; i < sPath.length; ++i){

            if(!obj.has(sPath[i]))
                continue;

            element = obj.get(sPath[i]);

            if(sPath.length == i + 1){
                return element;
            }

            if (element.isJsonObject() && element.getAsJsonObject().has(sPath[i + 1])){
                element = element.getAsJsonObject().get(sPath[i + 1]);
                return element;
            }

            if(element.isJsonArray())
                return element;
        }

        return element;
    }

    @Override
    public String getString(String path, String def) {
        JsonElement element = resolvePath(path);

        if(element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()){
            return element.getAsJsonPrimitive().getAsString();
        }

        return def;
    }

    @Override
    public int getInteger(String path, int def) {
        JsonElement element = resolvePath(path);

        if(element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()){
            return element.getAsJsonPrimitive().getAsInt();
        }

        return def;
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        JsonElement element = resolvePath(path);

        if(element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()){
            return element.getAsJsonPrimitive().getAsBoolean();
        }

        return def;
    }

    @Override
    public List<String> getStringList(String path, List<String> def) {
        JsonElement element = resolvePath(path);

        if(element == null || !element.isJsonArray()){
            return def;
        }

        List<String> stringList = new ArrayList<>();

        Iterator<JsonElement> it = element.getAsJsonArray().iterator();

        while(it.hasNext()){
            element = it.next();

            if(!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()){
                continue;
            }

            stringList.add(element.getAsString());
        }

        return def;
    }

    @Override
    public List<String> getKeys(String path, List<String> def) {
        return null;
    }

    @Override
    public Map<String, String> getStringMap(String path, Map<String, String> def) {
        return null;
    }

    @Override
    public JsonElement getElement(String path) {
        return resolvePath(path);
    }
}
