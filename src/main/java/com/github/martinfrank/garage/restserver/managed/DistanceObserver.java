package com.github.martinfrank.garage.restserver.managed;

import com.github.martinfrank.garage.restserver.GarageModel;
import com.github.martinfrank.garage.restserver.GarageRestServerConfiguration;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class DistanceObserver implements Managed {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceObserver.class);
    private final Timer timer = new Timer();

    public DistanceObserver(GarageRestServerConfiguration configuration, GarageModel model) {
    }

    @Override
    public void start() throws Exception {
        LOGGER.info("start");
        // scheduling the task at fixed rate delay
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkDistance();
            }
        }, 500, 1000);


    }

    private void checkDistance() {
        LOGGER.debug("measuring distance");
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("stop");
        timer.cancel();
        timer.purge();
    }

}
