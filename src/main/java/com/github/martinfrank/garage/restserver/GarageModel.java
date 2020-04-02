package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class GarageModel {

    private long toggleGatePushedTimestamp;

    private final GpioController gpio = GpioFactory.getInstance();
    ;

    private DistanceResults distanceResults = new DistanceResults();

}
