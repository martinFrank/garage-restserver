package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class GarageModel {

    private long toggleGatePushedTimestamp;

    private final GpioController gpio;
    private final GarageRestServerConfiguration configuration;

    private DistanceResults distanceResults = new DistanceResults();

    public GarageModel(GarageRestServerConfiguration configuration) {
        gpio = GpioFactory.getInstance();
        this.configuration = configuration;
    }

    public String getGateStatus() {
        return "gate status";
    }

    public String getGateLastUpdate() {
        return "gateLastUpdate";
    }

    public boolean isGateMoving() {
        return true;
    }

    public void openCloseGate() {

    }

    public GpioController getGpio() {
        return gpio;
    }

    public GarageRestServerConfiguration getConfiguration() {
        return configuration;
    }

    public DistanceResults getDistanceResults() {
        return distanceResults;
    }
}
