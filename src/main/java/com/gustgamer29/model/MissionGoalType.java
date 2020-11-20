package com.gustgamer29.model;

public enum MissionGoalType {

    KILL_A_PLAYER("combat.killing-player"),
    KILL_PLAYERS("combat.killing-players"),
    KILL_PASSIVE_MOBS("combat.killing-passive-mobs"),
    KILL_MONSTERS("combat.killing.monsters"),
    KILL_A_PASSIVE_MOB("combat.killing-passive-mob"),
    KILL_A_MONSTER("combat.killing-monster"),
    HARVEST_A_PLANT("harvest.harvesting-plant"),
    HARVEST_PLANTS("harvest.harvesting-plants"),
    GET_A_FISH("fishing.get-a-fish"),
    GET_FISHES("fishing.get-fishes"),
    STAY_ONLINE("passive.stay-online"),
    CRAFT_ITEM("passive.craft-item"),
    CRAFT_ITEMS("passive.craft-items"),
    EAT_SOMETHING("passive.eat-something"),
    PLACE_BLOCKS_OF_TYPE("passive.place-blocks-of"),
    PLACE_BLOCKS("passive.place-blocks"),
    BREAK_BLOCKS_OF_TYPE("passive.break-blocks-of"),
    BREAK_BLOCKS("passive.break-blocks");

    private String languageDir;

    MissionGoalType(String languageDir){
        this.languageDir = languageDir;
    }
    /*
        The path to goal name from a source language file
     */
    public String getLanguageDir(){
        return this.languageDir;
    }
}
