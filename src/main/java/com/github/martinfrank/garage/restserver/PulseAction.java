package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

public class PulseAction extends GpioAction {

    private final GpioPinDigitalOutput myPin;
    private final long pulseDurationInMillis;


    public PulseAction(GpioController gpio, Pin pin, long pulseDurationInMillis) {
        super(gpio);
        myPin = gpio.provisionDigitalOutputPin(pin,   // PIN NUMBER
                "My Pin",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)
        this.pulseDurationInMillis = pulseDurationInMillis;
    }

    @Override
    public void apply() {
        System.out.println("pulse pin " + myPin + " for " + pulseDurationInMillis + "ms");
        getGpio().pulse(pulseDurationInMillis, myPin);
    }
}
