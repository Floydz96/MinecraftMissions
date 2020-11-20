package com.gustgamer29.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Reward {

    @Getter @Setter
    private String command;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private MissionDifficulty difficulty;

}
