package com.gustgamer29.npc;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.database.internal.npc.NpcDaoStrategy;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;
import com.gustgamer29.util.Callback;

public interface INPC {

    Logger LOGGER = LoggerFactory.getLogger(INPC.class);

    String getId();

    String getName();
    String setName(String name);

    NpcLocation getLocation();
    void setLocation(NpcLocation location);

    NpcType getType();
    void setType(NpcType type);

    boolean isSpawned();

    boolean persist(DreamMissions plugin, NpcDaoStrategy persistStrategy);

    default void spawn(DreamMissions plugin){
        this.spawn(plugin, (status, error) -> {
            switch (status){
                case ERROR: {
                    LOGGER.error("An error occurred during execution of spawn method. {}", error);
                    break;
                }
                case INTERNAL_ERROR: {
                    LOGGER.error("An internal error occurred during execution of spawn method. {}", error);
                    break;
                }
            }
        });
    }

    void spawn(DreamMissions plugin, Callback<DispatchedActionStatus> statusCallback);

    void destroy();

    INPC copy();

    enum DispatchedActionStatus {
        EXECUTED, ERROR, INTERNAL_ERROR;
    }
}

