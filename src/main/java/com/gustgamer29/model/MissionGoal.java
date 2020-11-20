package com.gustgamer29.model;

import com.gustgamer29.DreamMissions;

public interface MissionGoal {

    DreamMissions getPlugin();

    double getPercentOfCompletion();

    CompletionStatus performAction(MissionGoalType type);

    enum CompletionStatus {
        IN_PROGRESS, COMPLETED
    }
}
