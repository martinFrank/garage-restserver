package com.github.martinfrank.garage.restserver.services;

import com.github.martinfrank.garage.restserver.GarageModel;
import com.github.martinfrank.garage.restserver.GarageRestServerConfiguration;
import com.github.martinfrank.garage.restserver.NotificationRequestUtil;
import com.github.martinfrank.garage.restserver.model.DistanceResults;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class OpenGateAlertService implements Managed {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenGateAlertService.class);
    private final Timer timer = new Timer();

    private final long poll_in_millis;
    private final long delay_in_millis;
    private final long alertTimeoutInMillis;

    private final DistanceResults distanceResults;
    private DistanceResults.Summary previousSummary;
    private long openDurationInMillis;
    private final String token;

    public OpenGateAlertService(GarageModel model, GarageRestServerConfiguration configuration) {
        distanceResults = model.getDistanceResults();
        poll_in_millis = configuration.timeConfig.alertPoll.toMilliseconds();
        delay_in_millis = configuration.timeConfig.alertDelay.toMilliseconds();
        alertTimeoutInMillis = configuration.timeConfig.alertAfterOpen.toMilliseconds();
        token = configuration.firebase.token;
    }

    @Override
    public void start() throws Exception {
        LOGGER.info("start");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                alertIfRequired();
            }
        }, delay_in_millis, poll_in_millis);
    }

    private void alertIfRequired() {
        updateOpenTime();
        if (openDurationInMillis > 0) {
            long timeRemaining = alertTimeoutInMillis - openDurationInMillis;
            LOGGER.info("gate is open - {}ms until sending an alert", timeRemaining);
        }
        if (openDurationInMillis > alertTimeoutInMillis) {
            String date = "Sendeuhrzeit:" + new Date(System.currentTimeMillis()).toString();
            try {
                NotificationRequestUtil.requestSendingPushNotification(
                        "Garage offen", "Die Garage ist offen!\n" + date, token);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openDurationInMillis = 0;
        }
    }

    private void updateOpenTime() {
        DistanceResults.Summary summary = distanceResults.getSummary();
        if (summary == DistanceResults.Summary.OPEN && previousSummary == DistanceResults.Summary.OPEN) {
            openDurationInMillis = openDurationInMillis + poll_in_millis;
            LOGGER.info("gate is open now for {}ms", openDurationInMillis);
        } else {
            openDurationInMillis = 0;
        }
        previousSummary = summary;
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("stop");
        timer.cancel();
        timer.purge();
    }

}
