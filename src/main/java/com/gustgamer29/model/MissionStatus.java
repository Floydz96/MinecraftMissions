package com.gustgamer29.model;

public class MissionStatus {

    private Status status;
    private float completion;

    public enum Status {
        COMPLETED, IN_PROGRESS, GETTING_STARTED
    }
}
