package com.gustgamer29.mission;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.model.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractMission implements Mission {

    @Getter(AccessLevel.PROTECTED)
    private DreamMissions plugin;

    private MPlayer player;

    @Setter(AccessLevel.PROTECTED)
    private MissionCategory category;

    @Setter(AccessLevel.PROTECTED)
    private MissionDifficulty difficulty;

    @Setter(AccessLevel.PROTECTED)
    private List<MissionGoal> missionGoal;

//    @Override
//    public String getName() {
//        return this.name;
//    }
//
//    @Override
//    public List<String> getDescription() {
//        return this.description;
//    }

    @Override
    public MissionCategory getCategory() {
        return this.category;
    }

    @Override
    public MissionDifficulty getMissionDifficulty() {
        return this.difficulty;
    }

    @Override
    public MPlayer getPlayer() {
        return this.player;
    }
}
