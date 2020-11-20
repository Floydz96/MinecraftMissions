package com.gustgamer29.mission;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.model.MissionGoal;
import com.gustgamer29.model.MissionGoalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractMissionGoal<T> implements MissionGoal {

    private final DreamMissions plugin;

    @Getter
    private MissionGoalType goalType;

    @Getter
    private float actualStatus;

    @Getter
    private float objectiveStatus;

    @Getter
    private T target;

    @Override
    public double getPercentOfCompletion() {

        if (actualStatus <= 0 || objectiveStatus <= 0)
            return 100;

        return  Math.round((actualStatus * objectiveStatus) / 100) * 0.5;
    }

    @Override
    public DreamMissions getPlugin() {
        return this.plugin;
    }
}
