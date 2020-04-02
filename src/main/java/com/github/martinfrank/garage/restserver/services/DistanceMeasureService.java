package com.github.martinfrank.garage.restserver.services;

import com.github.martinfrank.garage.restserver.GarageModel;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class DistanceMeasureService implements Managed {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceMeasureService.class);
    private final Timer timer = new Timer();
    //FIXME values from configuration!!!
    private static final int POLL_TIME_IN_MILLIS = 10000;
    private static final int DELAY_IN_MILLIS = 5000;

    private final GarageModel model;

    private final GpioPinDigitalOutput burstPin;
    private final GpioPinDigitalInput echoPin;

    public DistanceMeasureService(GarageModel model) {
        this.model = model;
        burstPin = model.getGpio().provisionDigitalOutputPin(
                RaspiPin.getPinByName(model.getConfiguration().pinConfig.burstPin),
                "BurstPin",
                PinState.LOW);
        echoPin = model.getGpio().provisionDigitalInputPin(
                RaspiPin.getPinByName(model.getConfiguration().pinConfig.echoPin),
                "EchoPin");
        LOGGER.info("distance measure observer startet with");
        LOGGER.info("burstpin {}", burstPin);
        LOGGER.info("echopin {}", echoPin);
    }

    @Override
    public void start() throws Exception {
        LOGGER.info("start");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkDistance();
                alertIfRequired();
            }
        }, DELAY_IN_MILLIS, POLL_TIME_IN_MILLIS);
    }

    private void alertIfRequired() {

    }

    private void checkDistance() {
        LOGGER.debug("measuring distance now");

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
            model.getDistanceResults().add(System.currentTimeMillis(), echoRunTime);
            LOGGER.info("result measuring distance: echoRunTime (shortened) {}", shortened);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            e.printStackTrace();
        }
        LOGGER.debug("measuring distance done");
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("stop");
        timer.cancel();
        timer.purge();
    }

}
