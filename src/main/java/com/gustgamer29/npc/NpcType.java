package com.gustgamer29.npc;

import java.util.stream.Stream;

public enum NpcType {

    FAKE_PLAYER,

    VILLAGER;

    NpcType() {
    }

    public static NpcType from(String name){
        return Stream.of(NpcType.values())
                .filter(npcType -> npcType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("NpcType with name " + name + " isn't found."));
    }
}
