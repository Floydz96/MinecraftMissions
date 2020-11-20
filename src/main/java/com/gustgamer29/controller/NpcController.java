package com.gustgamer29.controller;

import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcType;

import java.util.List;
import java.util.stream.Collectors;

public class NpcController extends AbstractMapController<String, INPC>{

    public NpcController() {
    }

    @Override
    protected Comparator<String> contains() {
        return String::equals;
    }

    public List<INPC> fromType(NpcType type){
        return getControllerMap().values().stream().filter(npc -> npc.getType().equals(type)).collect(Collectors.toList());
    }

    public INPC fromId(String id){
        if(!contains(id))
            return null;

        return getControllerMap().get(id);
    }

}
