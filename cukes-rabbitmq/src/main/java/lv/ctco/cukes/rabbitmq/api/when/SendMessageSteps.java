package lv.ctco.cukes.rabbitmq.api.when;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;
import lv.ctco.cukes.rabbitmq.facade.RequestFacade;

import java.util.Optional;

@Singleton
public class SendMessageSteps {

    @Inject
    RequestFacade requestFacade;

    @When("^the client sends message with routing key \"(.+)\"$")
    public void sendMessage(String routingKey) {
        requestFacade.sendMessage(Optional.empty(), routingKey);
    }

    @When("^the client sends message to exchange \"" + NamePatterns.EXCHANGE_NAME + "\" with routing key \"(.+)\"$")
    public void sendMessageToExchange(String exchange, String routingKey) {
        requestFacade.sendMessage(Optional.of(exchange), routingKey);
    }
}
