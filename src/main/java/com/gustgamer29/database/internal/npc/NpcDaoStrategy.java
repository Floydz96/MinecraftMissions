package com.gustgamer29.database.internal.npc;

import com.gustgamer29.database.internal.dao.npc.NpcDAO;
import com.gustgamer29.database.internal.npc.JsonNpcDaoImpl;
import org.bukkit.plugin.java.JavaPlugin;

public enum NpcDaoStrategy {

    JSON {
        @Override
        public NpcDAO invoke(JavaPlugin plugin) {
            return new JsonNpcDaoImpl(plugin);
        }
    };

    public abstract NpcDAO invoke(JavaPlugin plugin);
}
