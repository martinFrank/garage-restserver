package com.github.martinfrank.garage.restserver.services;

import com.github.martinfrank.garage.restserver.GarageModel;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class OpenGateAlertService implements Managed {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenGateAlertService.class);
    private final Timer timer = new Timer();
    //FIXME values from configuration!!!
    private static final int POLL_TIME_IN_MILLIS = 10000;
    private static final int DELAY_IN_MILLIS = 5000;

    private final GarageModel model;

    public OpenGateAlertService(GarageModel model) {
        this.model = model;
    }

    @Override
    public void start() throws Exception {
        LOGGER.info("start");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                alertIfRequired();
            }
        }, DELAY_IN_MILLIS, POLL_TIME_IN_MILLIS);
    }

    private void alertIfRequired() {
        LOGGER.debug("measuring distance now");
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("stop");
        timer.cancel();
        timer.purge();
    }

}
