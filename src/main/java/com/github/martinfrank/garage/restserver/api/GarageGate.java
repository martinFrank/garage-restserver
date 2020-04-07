package com.github.martinfrank.garage.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.martinfrank.garage.restserver.model.GarageGateModel;

public class GarageGate {

    private String state;
    private String lastRequest;
    private boolean isMoving;

    public GarageGate() {
        // Jackson deserialization
    }

    public GarageGate(GarageGateModel garageGate) {
        this.state = garageGate.getGateStatus();
        this.lastRequest = garageGate.getGateLastUpdate();
        this.isMoving = garageGate.isGateMoving();
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
