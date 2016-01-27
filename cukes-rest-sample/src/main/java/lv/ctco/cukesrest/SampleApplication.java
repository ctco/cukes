package lv.ctco.cukesrest;

import com.yammer.dropwizard.*;
import com.yammer.dropwizard.config.*;
import lv.ctco.cukesrest.resources.*;

public class SampleApplication extends Service<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        bootstrap.setName("cukes-rest-sample-app");
    }

    @Override
    public void run(SampleConfiguration configuration, Environment environment) {
        environment.addResource(new PingResource());
        environment.addHealthCheck(new SampleHealthCheck());
    }

}
