package com.gustgamer29.database.internal.mysql;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.database.internal.DatabaseDaoProvider;
import com.gustgamer29.database.internal.dao.player.MPlayerDao;
import com.gustgamer29.database.internal.mysql.dbimpl.MPlayerMysqlDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLProvider extends MySQL implements DatabaseDaoProvider {

    private static final String DB_PACKAGE_NAME = "com.mysql.jdbc.Driver";

    private DreamMissions plugin;
    private String url;

    public MySQLProvider(DreamMissions plugin, MySQLUrlBuilder builder) throws SQLException {
        this.plugin = plugin;
        this.url = builder.build();

        getConnection();
    }


    @Override
    public DreamMissions getPlugin() {
        return this.plugin;
    }

    @Override
    public Connection getConnection() throws SQLException {

        try {
            Class.forName(DB_PACKAGE_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(url);
    }

    @Override
    public void init() {
        PreparedStatement ps = null;
        try (Connection c = getConnection()){
            ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS DreamMissions_player(`id` varchar(100) NOT NULL, `nick` varchar(20) NOT NULL UNIQUE, `vitorias` bigint(10) NOT NULL, `derrotas` bigint(10) NOT NULL, `bau_size` int(2) DEFAULT '0', PRIMARY KEY(`id`), UNIQUE KEY(`nick`))ENGINE=InnoDB DEFAULT CHARSET=utf8");
            ps.executeUpdate();
            closeStatement(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    @Override
    public MPlayerDao mPlayerDao() {
        return new MPlayerMysqlDaoImpl(this);
    }
}
