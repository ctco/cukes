package lv.ctco.cukes.rabbitmq.api.then;

import cucumber.api.java.en.Then;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;

public class ReceiveMessageSteps {

    @Then("^wait for message in queue \"" + NamePatterns.QUEUE_NAME + "\"$")
    public void waitForMessageInQueue(String queue) {

    }

    @Then("^wait for message in queue \"" + NamePatterns.QUEUE_NAME + "\" bound to exchange \"" + NamePatterns.EXCHANGE_NAME + "\"$")
    public void waitForMessageInQueue(String queue, String exchange) {

    }

    @Then("^wait for message in queue \"" + NamePatterns.QUEUE_NAME + "\" for not more than (\\d+) seconds$")
    public void waitForMessageInQueue(String queue, Integer timeout) {

    }

    @Then("^wait for message in queue \"" + NamePatterns.QUEUE_NAME + "\" bound to exchange \"" + NamePatterns.EXCHANGE_NAME + "\" for not more than (\\d+) seconds$")
    public void waitForMessageInQueue(String queue, String exchange, Integer timeout) {

    }

}
