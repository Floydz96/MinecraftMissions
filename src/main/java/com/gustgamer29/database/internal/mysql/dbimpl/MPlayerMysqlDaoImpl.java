package com.gustgamer29.database.internal.mysql.dbimpl;

import com.gustgamer29.database.internal.dao.player.MPlayerDao;
import com.gustgamer29.database.internal.mysql.MySQLProvider;
import com.gustgamer29.model.MPlayer;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class MPlayerMysqlDaoImpl implements MPlayerDao {

    @Getter
    private MySQLProvider provider;

    public MPlayerMysqlDaoImpl(MySQLProvider provider) {
        this.provider = provider;
    }

    @Override
    public MPlayer getByName(String name) {
        return null;
    }

    @Override
    public MPlayer getById(UUID identifier) {
        return null;
    }

    @Override
    public List<MPlayer> getAll() {
        return null;
    }

    @Override
    public void save(MPlayer obj) {

    }

    @Override
    public void delete(UUID identifier) {

    }

    @Override
    public void update(UUID identifier, MPlayer obj) {

    }
}
