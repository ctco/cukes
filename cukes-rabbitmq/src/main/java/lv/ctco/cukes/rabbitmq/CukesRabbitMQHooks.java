package lv.ctco.cukes.rabbitmq;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.After;
import lv.ctco.cukes.rabbitmq.internal.ConnectionService;

@Singleton
public class CukesRabbitMQHooks {

    @Inject
    ConnectionService connectionService;

    @After
    public void afterScenario() {
        connectionService.initConfiguration();
    }
}
