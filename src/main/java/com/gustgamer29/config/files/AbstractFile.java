package com.gustgamer29.config.files;

import com.gustgamer29.config.adapter.ConfigurationAdapter;
import com.gustgamer29.config.adapter.GsonConfigurationAdapter;
import com.gustgamer29.config.json.ConfigKeys;
import com.gustgamer29.config.json.JsonKey;
import com.gustgamer29.config.json.LocationKeys;
import com.gustgamer29.exception.DuplicatedIdentifierException;
import com.gustgamer29.util.Callback;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter(AccessLevel.PROTECTED)
public abstract class AbstractFile {

    private final JavaPlugin plugin;

    @Getter
    private final Path path;

    @Getter
    private final ConfigurationAdapter adapter;

    protected AbstractFile(JavaPlugin plugin, String path, String rootContent) {
        this.plugin = plugin;
        this.path = Paths.get(path);
        this.adapter = new GsonConfigurationAdapter(plugin, this.path, rootContent);

        load();
    }

    public abstract void load();

    public void reload(){
        adapter.reload();
    }

    public abstract <V> V getDataFrom(JsonKey<V> key);
}
