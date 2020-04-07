package com.github.martinfrank.garage.restserver.services;

import com.github.martinfrank.garage.restserver.GarageModel;
import com.github.martinfrank.garage.restserver.GarageRestServerConfiguration;
import com.github.martinfrank.garage.restserver.model.DistanceResults;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class DistanceMeasureService implements Managed {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceMeasureService.class);
    private final Timer timer = new Timer();

    private final long poll_in_millis;
    private final long delay_in_millis;

    private final GpioPinDigitalOutput burstPin;
    private final GpioPinDigitalInput echoPin;
    private final DistanceResults distanceResults;

    public DistanceMeasureService(GarageModel model, GarageRestServerConfiguration configuration) {
        burstPin = model.getDigitalOutputPin(configuration.pinConfig.burstPin);
        echoPin = model.getDigitalInputPin(configuration.pinConfig.echoPin);
        distanceResults = model.getDistanceResults();
        LOGGER.info("distance measure observer started with burstPin {} and echoPin {}", burstPin, echoPin);
        poll_in_millis = configuration.timeConfig.distanceMeasurePoll.toMilliseconds();
        delay_in_millis = configuration.timeConfig.distanceMeasureDelay.toMilliseconds();
    }

    @Override
    public void start() throws Exception {
        LOGGER.info("start");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                measureDistance();
            }
        }, delay_in_millis, poll_in_millis);
    }

    private void measureDistance() {
        LOGGER.debug("measuring distance now");

        try {
            burstPin.high(); // Make trigger pin HIGH
            Thread.sleep((long) 0.01);// Delay for 10 microseconds
            burstPin.low(); //Make trigger pin LOW

            while (echoPin.isLow()) {
                //Wait until the ECHO pin gets HIGH
            }
            long startTime = System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
            while (echoPin.isHigh()) {
                //Wait until the ECHO pin gets LOW
            }
            long endTime = System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
            long echoRunTime = endTime - startTime;

            //FIXME MagicNumber
            if (echoRunTime > 7000000) {
                //cut down to 'normal' value
                echoRunTime = 6700000;
            }

            if (echoRunTime < 1200000) {
                //cut 'up' to 'normal' value
                echoRunTime = 1350000;
            }
            distanceResults.add(System.currentTimeMillis(), echoRunTime);

        } catch (Exception e) {
            LOGGER.error("Error during measuring distance", e);
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
