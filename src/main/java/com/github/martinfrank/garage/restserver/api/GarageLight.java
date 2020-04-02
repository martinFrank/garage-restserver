package com.github.martinfrank.garage.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GarageLight {

    private String state;
    private String lastRequest;
    private boolean isMoving;

    public GarageLight() {
        // Jackson deserialization
    }

    public GarageLight(String state, String lastRequest, boolean isMoving) {
        this.state = state;
        this.lastRequest = lastRequest;
        this.isMoving = isMoving;
    }

    @JsonProperty(value = "isMoving")
    public boolean isMoving() {
        return isMoving;
    }

    @JsonProperty
    public String getState() {
        return state;
    }

    @JsonProperty
    public String getLastRequest() {
        return lastRequest;
    }

}
