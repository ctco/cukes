package lv.ctco.cukesrest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lv.ctco.cukesrest.resources.PingResource;

public class SampleApplication extends Application<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "cukes-rest-sample-app";
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(SampleConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(new PingResource());
        environment.healthChecks().register("application", new SampleHealthCheck());
    }

}