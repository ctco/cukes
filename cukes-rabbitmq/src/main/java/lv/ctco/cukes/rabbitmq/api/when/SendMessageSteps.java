package lv.ctco.cukes.rabbitmq.api.when;

import cucumber.api.java.en.When;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;

public class SendMessageSteps {

    @When("^the client sends message with routing key \"(.+)\"$")
    public void sendMessage(String routingKey) {

    }

    @When("^the client sends message to exchange \"" + NamePatterns.EXCHANGE_NAME + "\" with routing key \"(.+)\"$")
    public void sendMessageToExchange(String exchange, String routingKey) {

    }
}
