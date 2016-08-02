package lv.ctco.cukesrest;

import com.google.inject.*;
import com.yammer.dropwizard.*;
import com.yammer.dropwizard.config.*;
import lv.ctco.cukesrest.gadgets.*;
import lv.ctco.cukesrest.healthcheck.*;

public class SampleApplication extends Service<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        args = new String[]{"server", "server.yml"};
        new SampleApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        bootstrap.setName("cukes-rest-sample-app");
    }

    @Override
    public void run(SampleConfiguration configuration, Environment environment) {
        Injector injector = Guice.createInjector();
        environment.addResource(injector.getInstance(GadgetResource.class));
        environment.addResource(injector.getInstance(StaticTypesResource.class));
        environment.addResource(injector.getInstance(CustomHeadersResource.class));
        environment.addHealthCheck(injector.getInstance(SampleHealthCheck.class));
    }
}
