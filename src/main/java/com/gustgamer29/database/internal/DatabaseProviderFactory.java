package com.gustgamer29.database.internal;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.config.json.ConfigKeys;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;

import java.sql.SQLException;

public class DatabaseProviderFactory {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseProviderFactory.class);

    public DreamMissions plugin;

    public DatabaseProviderFactory(DreamMissions plugin) throws SQLException {
        this.plugin = plugin;
    }

    public DatabaseProvider setupDatabase() throws SQLException {
        logger.info("Loading database..");

        AbstractUrlBuilder urlBuilder = plugin.getConfigManager().getLocationsFile()
                .getDataFrom(ConfigKeys.GENERAL_DATABASE_CONNECTION);

        DatabaseProvider provider = urlBuilder.getDbProviderType().resolveProvider(plugin, urlBuilder);
        provider.init();

        return provider;
    }
}
