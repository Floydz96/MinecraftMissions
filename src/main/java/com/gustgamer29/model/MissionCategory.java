package com.gustgamer29.model;

public enum MissionCategory {

    HIT_MAN(MissionGoalType.KILL_A_MONSTER,
            MissionGoalType.KILL_A_PASSIVE_MOB,
            MissionGoalType.KILL_A_PLAYER,
            MissionGoalType.KILL_MONSTERS,
            MissionGoalType.KILL_PASSIVE_MOBS,
            MissionGoalType.KILL_PLAYERS), ANIMAL_HUNTER, HERBALIST, FISHERMAN, MINER, MONSTER_KILLER, PASSIVE_PLAYER;

    private MissionGoalType[] goals;

    MissionCategory(MissionGoalType... goals){
        this.goals = goals;
    }

    public MissionGoalType[] getGoals(){
        return this.goals;
    }
}
