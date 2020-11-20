package com.gustgamer29.model;

import java.util.UUID;

public interface MPlayer {

    String getName();

    UUID getId();

    int getCompletedMissions();

    void sendMessage(String message);

}
