package com.gustgamer29.database.internal;

import com.gustgamer29.DreamMissions;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseProvider extends DatabaseDaoProvider{

    DreamMissions getPlugin();

    Connection getConnection() throws SQLException;

    void init();
}
