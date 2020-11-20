package com.gustgamer29.npc.storage;

import com.gustgamer29.npc.INPC;

public interface NpcStorage {

    void save(INPC npc);

    void remove(INPC npc);

    INPC get(String id);
}
