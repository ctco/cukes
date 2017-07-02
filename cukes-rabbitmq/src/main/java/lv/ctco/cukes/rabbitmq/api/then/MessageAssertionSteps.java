package lv.ctco.cukes.rabbitmq.api.then;

import cucumber.api.java.en.Then;

public class MessageAssertionSteps {

    @Then("^message body is empty$")
    public void assertMessageBodyIsEmpty() {

    }

    @Then("^message body is not empty$")
    public void assertMessageBodyIsNotEmpty() {

    }

    @Then("^message body equals to \"(.*)\"$")
    public void assertMessageBodyEqualsTo(String body) {

    }

    @Then("^message body not equal to \"(.*)\"$")
    public void assertMessageBodyDoesNotEqualTo(String body) {

    }

    @Then("^message body contains \"(.+)\"$")
    public void assertMessageBodyContains(String body) {
    }

    @Then("^message body does not contain \"(.+)\"$")
    public void assertMessageBodyDoesNotContain(String body) {

    }

}
