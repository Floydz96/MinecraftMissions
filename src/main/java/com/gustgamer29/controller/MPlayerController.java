package com.gustgamer29.controller;

import com.gustgamer29.model.MPlayer;

import java.util.UUID;

public class MPlayerController extends AbstractMapController<UUID, MPlayer> {

    @Override
    protected Comparator<UUID> contains() {
        return (from, to) -> from.compareTo(to) == 0;
    }
}

