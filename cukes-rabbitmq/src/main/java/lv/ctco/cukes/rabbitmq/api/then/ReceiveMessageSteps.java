package lv.ctco.cukes.rabbitmq.api.then;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;
import lv.ctco.cukes.rabbitmq.facade.ResponseFacade;

import java.util.Optional;

@Singleton
public class ReceiveMessageSteps {

    @Inject
    ResponseFacade responseFacade;


    @Then("^wait for message in queue \"" + NamePatterns.QUEUE_NAME + "\"$")
    public void waitForMessageInQueue(String queue) {
        responseFacade.waitForMessage(queue, Optional.empty());
    }

    @Then("^wait for message in queue \"" + NamePatterns.QUEUE_NAME + "\" for not more than (\\d+) seconds$")
    public void waitForMessageInQueue(String queue, Integer timeout) {
        responseFacade.waitForMessage(queue, Optional.of(timeout));
    }

}
