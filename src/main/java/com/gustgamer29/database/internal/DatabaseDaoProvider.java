package com.gustgamer29.database.internal;

import com.gustgamer29.database.internal.dao.player.MPlayerDao;

public interface DatabaseDaoProvider {

    MPlayerDao mPlayerDao();

}
