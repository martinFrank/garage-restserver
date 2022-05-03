package com.github.martinfrank.garage.restserver.resources;

import com.github.martinfrank.garage.restserver.api.Notification;
import com.github.martinfrank.garage.restserver.model.GarageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/garage/gate")
@Produces(MediaType.APPLICATION_JSON)
public class GarageGateResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageGateResource.class);

    private final GarageModel model;

    public GarageGateResource(GarageModel model) {
        this.model = model;
    }

    @POST
    public Notification openCloseGate() {
        LOGGER.info("openCloseGate");
        model.openCloseGate();

        return new Notification(new Date(System.currentTimeMillis()).toString());
    }

}
