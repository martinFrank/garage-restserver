package com.github.martinfrank.garage.restserver.resources;

import com.github.martinfrank.garage.restserver.GarageModel;
import com.github.martinfrank.garage.restserver.api.GarageLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/garage/light")
@Produces(MediaType.APPLICATION_JSON)
public class GarageLightResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageLightResource.class);

    private final GarageModel model;

    public GarageLightResource(GarageModel model) {
        this.model = model;
    }

    @GET
    public GarageLight getLight() {
        LOGGER.info("getLight");
        return new GarageLight("unknown", "16.12.1977", false);
    }

    @POST
    public GarageLight onOffLight() {
        LOGGER.info("onOffLight");
        return new GarageLight("unknown", "16.12.1977", true);
    }

}
