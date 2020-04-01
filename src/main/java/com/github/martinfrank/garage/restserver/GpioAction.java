package com.github.martinfrank.garage.restserver;

import com.pi4j.io.gpio.GpioController;

public abstract class GpioAction extends SubmitAction {

    private final GpioController gpio;

    public GpioAction(GpioController gpio) {
        this.gpio = gpio;
    }

    public GpioController getGpio() {
        return gpio;
    }
}
