package com.gustgamer29.model.blockminer;

import com.gustgamer29.model.MissionDifficulty;
import lombok.Getter;

public class BlockOccurrence {

    @Getter
    private final String id;

    @Getter
    private BlockOccurrenceRange easy;

    @Getter
    private BlockOccurrenceRange normal;

    @Getter
    private BlockOccurrenceRange hard;

    public BlockOccurrence(String id) {
        this.id = id;
    }

    public BlockOccurrenceRange fromDifficulty(MissionDifficulty diff){
        switch (diff){
            case EASY:
                return easy;
            case MEDIUM:
                return normal;
            case HARD:
                return hard;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void setEasy(String easy) {
        this.easy = new BlockOccurrenceRange(easy);
    }

    public void setNormal(String normal) {
        this.normal = new BlockOccurrenceRange(normal);
    }

    public void setHard(String hard) {
        this.hard = new BlockOccurrenceRange(hard);
    }
}
