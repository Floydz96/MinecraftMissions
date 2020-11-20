package com.gustgamer29.database.internal;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.database.internal.mysql.MySQLProvider;
import com.gustgamer29.database.internal.mysql.MySQLUrlBuilder;

import java.sql.SQLException;
import java.util.stream.Stream;

public enum DatabaseProviderStrategy {

    MYSQL {
        @Override
        public DatabaseProvider resolveProvider(DreamMissions plugin, AbstractUrlBuilder urlBuilder) throws SQLException {
            return new MySQLProvider(plugin, (MySQLUrlBuilder) urlBuilder);
        }
    },

    H2 {
        @Override
        public DatabaseProvider resolveProvider(DreamMissions plugin, AbstractUrlBuilder urlBuilder) {
            throw new UnsupportedOperationException("H2 isn't supported yet.");
        }
    },

    MONGODB {
        @Override
        public DatabaseProvider resolveProvider(DreamMissions plugin, AbstractUrlBuilder urlBuilder) {
            throw new UnsupportedOperationException("MongoDB isn't supported yet.");
        }
    };

    DatabaseProviderStrategy() {
    }

    public abstract DatabaseProvider resolveProvider(DreamMissions plugin, AbstractUrlBuilder urlBuilder) throws SQLException;

    public static DatabaseProviderStrategy fromString(String name) {
        return Stream.of(DatabaseProviderStrategy.values())
                .filter(dbProviderName -> dbProviderName.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
