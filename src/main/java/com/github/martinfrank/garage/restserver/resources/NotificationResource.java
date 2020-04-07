package com.github.martinfrank.garage.restserver.resources;

import com.github.martinfrank.garage.restserver.NotificationRequestUtil;
import com.github.martinfrank.garage.restserver.api.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Date;

@Path("/garage/notification")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationResource.class);

    @GET
    public Notification getNotification() {
        LOGGER.info("notification");

        try {
            NotificationRequestUtil.requestSendingPushNotification(
                    "Test", "Dies ist eine Test-Notifikation");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Notification(new Date(System.currentTimeMillis()).toString());
    }

}
