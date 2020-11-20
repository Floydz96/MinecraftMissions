package com.gustgamer29.model.blockminer;

import lombok.Getter;

import java.util.Objects;
import java.util.Random;

public class BlockOccurrenceRange {

    @Getter
    private int min;

    @Getter
    private int max;

    private static final Random RANDOM = new Random();

    public BlockOccurrenceRange(String occurrence) {
        Objects.requireNonNull(occurrence, "occurrence");

        String[] offset = occurrence.split("-");

        if(offset.length != 2)
            throw new RuntimeException(occurrence);

        min = Integer.parseInt(offset[0]);
        max = Integer.parseInt(offset[1]);
    }

    public int nextInteger(){
        return min + new Random().nextInt(Math.abs(max - min));
    }

    @Override
    public String toString() {
        return "Max: " + max + " Min: " + min;
    }
}
