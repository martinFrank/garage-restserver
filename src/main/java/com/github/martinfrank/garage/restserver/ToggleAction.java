package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

public class ToggleAction extends GpioAction {

    private final GpioPinDigitalOutput myPin;

    public ToggleAction(GpioController gpio, Pin pin) {
        super(gpio);
        myPin = gpio.provisionDigitalOutputPin(pin,   // PIN NUMBER
                "My Pin",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)

    }

    @Override
    public void apply() {
        System.out.println("toggle pin " + myPin);
        getGpio().toggle(myPin);
    }
}
