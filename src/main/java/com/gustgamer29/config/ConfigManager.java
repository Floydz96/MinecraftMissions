package com.gustgamer29.config;

import com.gustgamer29.config.files.LocationsFile;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    @Getter
    private final JavaPlugin plugin;

    @Getter
    private final LocationsFile locationsFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;

        this.locationsFile = new LocationsFile(plugin);
    }

}
