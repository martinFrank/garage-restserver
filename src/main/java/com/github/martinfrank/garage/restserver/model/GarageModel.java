package com.github.martinfrank.garage.restserver.model;

import com.github.martinfrank.garage.restserver.GarageRestServerConfiguration;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;

public class GarageModel {

    //FIXME from config
    private static final int GATE_PULS_DURATION_IN_MILLIS = 250;
    private static final int PIN_LED = 24; // PIN 18 = BCM 24
    private DigitalOutput gateToggle;
    private boolean isRunning = false;



    public GarageModel(GarageRestServerConfiguration configuration) {
        Context pi4j = Pi4J.newAutoContext();
        DigitalOutputConfigBuilder ledConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .name("LED Flasher")
                .address(PIN_LED)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");

        gateToggle = pi4j.create(ledConfig);
    }

    public void openCloseGate() {
        if(isRunning){
            new Thread(() -> {
                isRunning = true;
                gateToggle.high();
                try {
                    Thread.sleep(GATE_PULS_DURATION_IN_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gateToggle.low();
                isRunning = false;
            }).start();
        }
    }

}
