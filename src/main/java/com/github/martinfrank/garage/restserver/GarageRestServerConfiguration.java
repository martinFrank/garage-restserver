package com.github.martinfrank.garage.restserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.util.Duration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;


public class GarageRestServerConfiguration extends Configuration {

    @JsonProperty("pinConfig")
    public PinConfig pinConfig;

    @NotNull
    private Map<String, Map<String, String>> viewRendererConfiguration = Collections.emptyMap();


    public static final class PinConfig {

        @JsonProperty("burstPin")
        public String burstPin;

        @JsonProperty("echoPin")
        public String echoPin;

        @JsonProperty("gatePin")
        public String gatePin;
    }

}
