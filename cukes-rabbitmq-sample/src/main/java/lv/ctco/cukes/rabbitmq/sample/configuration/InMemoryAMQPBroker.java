package lv.ctco.cukes.rabbitmq.sample.configuration;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("amqpBroker")
public class InMemoryAMQPBroker {

    private final Broker broker = new Broker();

    BrokerOptions brokerOptions() {
        BrokerOptions brokerOptions = new BrokerOptions();

        brokerOptions.setConfigProperty("qpid.amqp_port", "5672");
        brokerOptions.setInitialConfigurationLocation(getClass().getResource("/initial-config.json").getFile());
        brokerOptions.setStartupLoggedToSystemOut(false);
        return brokerOptions;
    }

    @PostConstruct
    public void start() throws Exception {
        broker.startup(brokerOptions());
    }

    @PreDestroy
    public void stop() {
        broker.shutdown();
    }
}
