package com.github.martinfrank.garage.restserver.resources;

import com.github.martinfrank.garage.restserver.GarageModel;
import com.github.martinfrank.garage.restserver.api.GarageGate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/garage/gate")
@Produces(MediaType.APPLICATION_JSON)
public class GarageGateResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageGateResource.class);

    private final GarageModel model;

    public GarageGateResource(GarageModel model) {
        this.model = model;
    }

    @GET
    public GarageGate getGate() {
        LOGGER.info("getGate");
        return new GarageGate("unknown", "16.12.1977", false);
    }

    @POST
    public GarageGate openCloseGate() {
        try {
            model.openCloseGate();
            return new GarageGate(model.getGateStatus(), model.getGateLastUpdate(), model.isGateMoving());
        } catch (Exception e) {

        }
        LOGGER.info("openCloseGate");
        return new GarageGate("unknown", "16.12.1977", true);
    }

}
