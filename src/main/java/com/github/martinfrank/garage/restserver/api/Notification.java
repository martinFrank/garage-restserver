package com.github.martinfrank.garage.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {

    private String timestamp;

    public Notification() {
        // Jackson deserialization
    }

    public Notification(String timeStamp) {
        this.timestamp = timeStamp;
    }

    @JsonProperty
    public String getTimestamp() {
        return timestamp;
    }

}
