package com.github.martinfrank.garage.restserver.model;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.Date;

public class GarageGateModel {

    private long timeStampOpenClose;
    private final GpioPinDigitalOutput togglePin;
    private final long pulseDurationInMillis;
    private final GpioController gpio;
    private final DistanceResults distanceResults;

    public GarageGateModel(
            GpioPinDigitalOutput togglePin,
            int pulseDurationInMillis,
            GpioController gpio,
            DistanceResults distanceResults) {
        this.togglePin = togglePin;
        this.pulseDurationInMillis = pulseDurationInMillis;
        this.gpio = gpio;
        this.distanceResults = distanceResults;
    }

    public void openClose() {
        timeStampOpenClose = System.currentTimeMillis();
        gpio.pulse(pulseDurationInMillis, togglePin);
    }


    public String getGateStatus() {
        return distanceResults.getSummary().name();
    }

    public String getGateLastUpdate() {
        return new Date(timeStampOpenClose).toString();
    }

    public boolean isGateMoving() {
        return true;
    }

}
