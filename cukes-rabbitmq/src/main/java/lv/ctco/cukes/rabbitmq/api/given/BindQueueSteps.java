package lv.ctco.cukes.rabbitmq.api.given;

import cucumber.api.java.en.Given;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;

public class BindQueueSteps {

    @Given("^bind queue \"" + NamePatterns.QUEUE_NAME + "\" to exchange \"" + NamePatterns.EXCHANGE_NAME + "\" with routing key \"(.+)\"$")
    public void bindQueueToExchangeWithRoutingKey(String queueName, String exchange, String routingKey) {
    }

    @Given("^bind queue \"" + NamePatterns.QUEUE_NAME + "\" with routing key \"(.+)\"$")
    public void bindQueueToDefaultExchangeWithRoutingKey(String queueName, String routingKey) {
    }

}
