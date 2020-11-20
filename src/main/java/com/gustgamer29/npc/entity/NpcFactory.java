package com.gustgamer29.npc.entity;

import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcLocation;
import com.gustgamer29.npc.NpcType;

public class NpcFactory {

    public static INPC of(String id, String name, NpcLocation loc, NpcType type){

        if(type.equals(NpcType.VILLAGER))
            return new VillagerNPC(id, name, loc);

        if(type.equals(NpcType.FAKE_PLAYER))
            return new PlayerNPC(id, name, loc);

        return null;
    }

}
