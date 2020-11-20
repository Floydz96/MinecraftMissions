package com.gustgamer29.config.files;

import com.gustgamer29.config.json.JsonKey;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;
import org.apache.commons.io.Charsets;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class LocationsFile extends AbstractFile {

    private static final String DEFAULT_FILE_CONTENT = "{\"Locations\":{\"npc-locations\": []}}";

    private static final Consumer<File> WRITE_DEFAULTS = (f) -> {

        if(f == null)
            return;

        try(BufferedWriter bw = Files.newBufferedWriter(f.toPath(), Charsets.UTF_8)){
            bw.write(DEFAULT_FILE_CONTENT);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    };

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationsFile.class);
    private static final String FILE_NAME = "npc-locations.json";

    public LocationsFile(JavaPlugin plugin) {
        super(plugin, plugin.getDataFolder().getAbsolutePath() + "\\" + FILE_NAME, "Locations");
    }

    @Override
    public void load() {
        LOGGER.debug(getPath().toString());
        LOGGER.info("Loading {}...", FILE_NAME);

        try {
            if (!Files.exists(getPath())) {
                if (getPlugin().getResource(FILE_NAME) != null) {
                    LOGGER.info("File doesn't exists.. Trying to load it from plugin resources..");
                    getPlugin().saveResource(FILE_NAME, true);
                } else {
                    LOGGER.info("File doesn't exists.. Trying to create a new one..");
                    Path path = Files.createFile(getPath());

                    if(path == null)
                        throw new RuntimeException("was not possible to create a file at plugins folder. Can it be a lack of privileges?");

                    WRITE_DEFAULTS.accept(path.toFile());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <V> V getDataFrom(JsonKey<V> key) {
        return key.get(this.getAdapter());
    }

}
