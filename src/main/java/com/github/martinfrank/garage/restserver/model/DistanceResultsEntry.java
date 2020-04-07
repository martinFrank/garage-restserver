package com.github.martinfrank.garage.restserver.model;

public class DistanceResultsEntry {

    private final long timeStamp;
    private final long echoRunTime;

    public DistanceResultsEntry(long timeStamp, long echoRunTime) {
        this.timeStamp = timeStamp;
        this.echoRunTime = echoRunTime;
    }

    public long getEchoRunTime() {
        return echoRunTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
