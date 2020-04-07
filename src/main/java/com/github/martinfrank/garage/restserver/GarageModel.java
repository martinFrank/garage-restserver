package com.github.martinfrank.garage.restserver;

import com.github.martinfrank.garage.restserver.model.DistanceResults;
import com.github.martinfrank.garage.restserver.model.GarageGateModel;
import com.pi4j.io.gpio.*;

public class GarageModel {

    //FIXME from config
    private static final int GATE_PULS_DURATION_IN_MILLIS = 250;
    private final GpioController gpio;

    private DistanceResults distanceResults = new DistanceResults();
    private final GarageGateModel gateModel;


    public GarageModel(GarageRestServerConfiguration configuration) {
        gpio = GpioFactory.getInstance();
        gateModel = new GarageGateModel(
                getDigitalOutputPin(configuration.pinConfig.gatePin),
                GATE_PULS_DURATION_IN_MILLIS,
                gpio, distanceResults);
    }

    public void openCloseGate() {
        gateModel.openClose();
    }

    public DistanceResults getDistanceResults() {
        return distanceResults;
    }

    public GarageGateModel getGarageGate() {
        return gateModel;
    }

    public GpioPinDigitalOutput getDigitalOutputPin(String configuredPinName) {
        return gpio.provisionDigitalOutputPin(
                RaspiPin.getPinByName(configuredPinName),
                configuredPinName,
                PinState.LOW);
    }

    public GpioPinDigitalInput getDigitalInputPin(String configuredPinName) {
        return gpio.provisionDigitalInputPin(
                RaspiPin.getPinByName(configuredPinName),
                configuredPinName);
    }

}
