package com.gustgamer29.controller;

import com.gustgamer29.model.MPlayer;
import com.gustgamer29.model.Mission;

public class MissionController extends AbstractMapController<MPlayer, Mission>{

    public MissionController() {
    }

    @Override
    protected Comparator<MPlayer> contains() {
        return (p1, p2) -> p1.getId().compareTo(p2.getId()) == 0;
    }

    public Mission getMissionFromPlayer(MPlayer player){
        return this.getControllerMap().entrySet()
                .stream()
                .filter(e -> this.contains(e.getKey()))
                .findFirst()
                .orElseThrow(RuntimeException::new).getValue();
    }
}
