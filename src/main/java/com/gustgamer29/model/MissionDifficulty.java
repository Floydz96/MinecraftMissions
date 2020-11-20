package com.gustgamer29.model;

import com.google.gson.reflect.TypeToken;

import java.util.stream.Stream;

public enum MissionDifficulty {

    EASY(1), MEDIUM(2), HARD(3);

    private static final TypeToken<MissionDifficulty> TYPE = TypeToken.get(MissionDifficulty.class);
    private int offset;

    MissionDifficulty(int offset) {
        this.offset = offset;
    }

    public int getOffset(){
        return this.offset;
    }

    public MissionDifficulty fromOffset(int offset){
        return Stream.of(MissionDifficulty.values())
                .filter(missionType -> missionType.getOffset() == offset)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
