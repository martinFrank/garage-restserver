package com.github.martinfrank.garage.restserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.martinfrank.garage.restserver.core.Template;
import io.dropwizard.Configuration;
import io.dropwizard.util.Duration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

//import java.time.Duration;

public class GarageRestServerConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @JsonProperty("pinConfig")
    public PinConfig pinConfig;

    @JsonProperty("timeConfig")
    public TimeConfig timeConfig;

    @JsonProperty("firebase")
    public Firebase firebase;

    @NotNull
    private Map<String, Map<String, String>> viewRendererConfiguration = Collections.emptyMap();

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }

    @JsonProperty("viewRendererConfiguration")
    public Map<String, Map<String, String>> getViewRendererConfiguration() {
        return viewRendererConfiguration;
    }

    @JsonProperty("viewRendererConfiguration")
    public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
        this.viewRendererConfiguration = viewRendererConfiguration;
    }

    public static final class PinConfig {

        @JsonProperty("burstPin")
        public String burstPin;

        @JsonProperty("echoPin")
        public String echoPin;

        @JsonProperty("gatePin")
        public String gatePin;
    }

    public static final class TimeConfig {

        @JsonProperty("distanceMeasureDelay")
        public Duration distanceMeasureDelay;

        @JsonProperty("distanceMeasurePoll")
        public Duration distanceMeasurePoll;

        @JsonProperty("alertDelay")
        public Duration alertDelay;

        @JsonProperty("alertPoll")
        public Duration alertPoll;

        @JsonProperty("alertAfterOpen")
        public Duration alertAfterOpen;
    }

    public static final class Firebase {

        @JsonProperty("token")
        public String token;
    }
}
