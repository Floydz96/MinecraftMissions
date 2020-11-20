package com.gustgamer29.mission.missions.block;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.controller.MissionController;
import com.gustgamer29.mission.AbstractMission;
import com.gustgamer29.model.MPlayer;
import com.gustgamer29.model.MissionCategory;
import com.gustgamer29.model.MissionDifficulty;
import com.gustgamer29.model.MissionStatus;
import com.gustgamer29.model.blockminer.BlockOccurrence;

import java.util.Arrays;
import java.util.List;

public class BlockMinerMission extends AbstractMission {

    private BlockOccurrence blockOccurrence;
    private MissionController controller;

    public BlockMinerMission(DreamMissions plugin, MPlayer player, MissionDifficulty difficulty, BlockOccurrence occurrence) {
        super(plugin, player, MissionCategory.MINER, difficulty);
        this.controller = plugin.getMissionController();
        this.blockOccurrence = occurrence;
    }


    @Override
    public void start() {
        controller.register(getPlayer(), this);
    }

    @Override
    public void end(EndReason reason) {
        controller.unregister(getPlayer());
    }

    @Override
    public void updateProgress() {

    }

    @Override
    public MissionStatus status() {
        return blockOccurrence.fromDifficulty(getMissionDifficulty()).;
    }
}
