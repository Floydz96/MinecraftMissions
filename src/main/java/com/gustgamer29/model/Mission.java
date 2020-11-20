package com.gustgamer29.model;

import java.util.List;

public interface Mission {

//    String getName();

//    List<String> getDescription();

    MissionCategory getCategory();

    MissionDifficulty getMissionDifficulty();

    List<MissionGoal> getMissionGoals();

    void start();

    void end(EndReason reason);

    void performAction(MissionActionType action, int count);

    MissionStatus status();

    MPlayer getPlayer();

    enum EndReason {
        PLAYER_COMMAND, STAFF_COMMAND, TIME_ENDS_UP;
    }
}
