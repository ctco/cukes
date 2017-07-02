package lv.ctco.cukes.rabbitmq.api.given;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;
import lv.ctco.cukes.rabbitmq.facade.SetupFacade;

import java.util.Optional;

@Singleton
public class BindQueueSteps {

    @Inject
    SetupFacade setupFacade;

    @Given("^bind queue \"" + NamePatterns.QUEUE_NAME + "\" to exchange \"" + NamePatterns.EXCHANGE_NAME + "\" with routing key \"(.+)\"$")
    public void bindQueueToExchangeWithRoutingKey(String queueName, String exchange, String routingKey) {
        setupFacade.declareQueue(queueName, Optional.of(exchange), routingKey);
    }

    @Given("^bind queue \"" + NamePatterns.QUEUE_NAME + "\" with routing key \"(.+)\"$")
    public void bindQueueToDefaultExchangeWithRoutingKey(String queueName, String routingKey) {
        setupFacade.declareQueue(queueName, Optional.empty(), routingKey);
    }

}
