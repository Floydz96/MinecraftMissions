package com.gustgamer29.npc.entity;

import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcLocation;
import com.gustgamer29.npc.NpcType;

public abstract class AbstractNPC implements INPC {

    private final String id;
    private String name;
    private NpcLocation loc;
    private NpcType type;
    private boolean isSpawned = false;

    public AbstractNPC(String id, String name, NpcLocation loc, NpcType type) {
        this.id = id;
        this.name = name;
        this.loc = loc;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String setName(String name) {
        String nameBeforeChange = this.name;

        this.name = name;

        return nameBeforeChange;
    }

    @Override
    public NpcLocation getLocation() {
        return this.loc;
    }

    @Override
    public void setLocation(NpcLocation location) {
        this.loc = location;
        this.destroy();
    }

    @Override
    public NpcType getType() {
        return this.type;
    }

    @Override
    public void setType(NpcType type) {
        this.type = type;
    }

    @Override
    public boolean isSpawned() {
        return this.isSpawned;
    }

    protected void setSpawned(boolean spawned){
        this.isSpawned = spawned;
    }
}
