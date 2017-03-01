package lv.ctco.cukesrest;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import lv.ctco.cukesrest.gadgets.GadgetResource;
import lv.ctco.cukesrest.healthcheck.CustomHeadersResource;
import lv.ctco.cukesrest.healthcheck.StaticTypesResource;
import org.slf4j.LoggerFactory;

public class SampleApplication extends Service<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        overrideLogging();
        bootstrap.setName("cukes-rest-sample-app");
    }

    @Override
    public void run(SampleConfiguration configuration, Environment environment) throws JoranException {
        overrideLogging();
        Injector injector = Guice.createInjector();
        environment.addResource(injector.getInstance(GadgetResource.class));
        environment.addResource(injector.getInstance(StaticTypesResource.class));
        environment.addResource(injector.getInstance(CustomHeadersResource.class));
        environment.addHealthCheck(injector.getInstance(SampleHealthCheck.class));
    }

    public static void overrideLogging() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        ContextInitializer initializer = new ContextInitializer(context);
        try {
            initializer.autoConfig();
        } catch (JoranException ignored) {}
    }
}
