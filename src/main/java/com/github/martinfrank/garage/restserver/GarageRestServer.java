package com.github.martinfrank.garage.restserver;

import com.github.martinfrank.garage.restserver.cli.RenderCommand;
import com.github.martinfrank.garage.restserver.core.Template;
import com.github.martinfrank.garage.restserver.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class GarageRestServer extends Application<GarageRestServerConfiguration> {
    public static void main(String[] args) throws Exception {
        new GarageRestServer().run(args);
    }


    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<GarageRestServerConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());


    }

    @Override
    public void run(GarageRestServerConfiguration configuration, Environment environment) {
        final Template template = configuration.buildTemplate();
        environment.jersey().register(new HelloWorldResource(template));


        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
//        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
//        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "*,GET,PUT,POST,DELETE,HEAD");


        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}