package com.gustgamer29.database.internal.dao.player;

import com.gustgamer29.database.internal.dao.DAO;
import com.gustgamer29.model.MPlayer;

import java.util.UUID;

public interface MPlayerDao extends DAO<UUID, MPlayer> {

    MPlayer getByName(String name);

}
