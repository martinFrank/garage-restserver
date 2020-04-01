package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.*;

public class MeasureDistanceAction extends GpioAction {


    private final GpioPinDigitalOutput burstPin;
    private final GpioPinDigitalInput echoPin;

    public MeasureDistanceAction(GpioController gpio, Pin burstPin, Pin echoPin) {
        super(gpio);
        this.burstPin = gpio.provisionDigitalOutputPin(burstPin,   // PIN NUMBER
                "BurstPin",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)
        this.echoPin = gpio.provisionDigitalInputPin(echoPin,   // PIN NUMBER
                "EchoPin");      // PIN STARTUP STATE (optional)
    }

    @Override
    public void apply() {

        try {
            burstPin.high(); // Make trigger pin HIGH
            Thread.sleep((long) 0.01);// Delay for 10 microseconds
            burstPin.low(); //Make trigger pin LOW

            while (echoPin.isLow()) { //Wait until the ECHO pin gets HIGH

            }
            long startTime = System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
            while (echoPin.isHigh()) { //Wait until the ECHO pin gets LOW

            }
            long endTime = System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
            long echoRunTime = endTime - startTime;
            long shortened = echoRunTime / 100000;
            System.out.println("shortened:" + shortened);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            e.printStackTrace();
        }

    }
}
