package com.github.martinfrank.garage.restserver.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;

public class DistanceResults {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceResults.class);

    private static final int CLOSED_OFFSET = 64000000;
    private static final int OPEN_OFFSET = 15000000;
    private static final int MAX_RESULT_SIZE = 10;
    private int index = 0;
    private final DistanceResultsEntry[] entries = new DistanceResultsEntry[MAX_RESULT_SIZE];

    public DistanceResults() {
        for (int i = 0; i < MAX_RESULT_SIZE; i++) {
            add(System.currentTimeMillis(), 0);
        }
    }

    public void add(long timeStamp, long echoRunTime) {
        addEntry(new DistanceResultsEntry(timeStamp, echoRunTime));
        increaseIndex();
    }

    private void increaseIndex() {
        index = index + 1;
        if (index >= MAX_RESULT_SIZE) {
            index = 0;
        }
    }

    public Summary getSummary() {
        long sum = Arrays.stream(entries).mapToLong(DistanceResultsEntry::getEchoRunTime).sum();
        LOGGER.debug("summary sum {}", sum);

        if (sum > CLOSED_OFFSET) {
            return Summary.CLOSED;
        }
        if (sum < OPEN_OFFSET) {
            return Summary.OPEN;
        }
        return Summary.UNKNOWN;
    }

    private void addEntry(DistanceResultsEntry entry) {
        entries[index] = entry;
    }


    public void debugValues() {
        LOGGER.info("------------");
        Arrays.stream(entries).forEach(e -> LOGGER.info("measured: {}  -  {} ", new Date(e.getTimeStamp()), e.getEchoRunTime()));
        LOGGER.info("------------");
    }

    public enum Summary {OPEN, CLOSED, UNKNOWN}
}
