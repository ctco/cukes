package lv.ctco.cukes.rabbitmq.api.then;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.rabbitmq.facade.ResponseFacade;
import org.apache.commons.lang3.NotImplementedException;

@Singleton
public class MessageAssertionSteps {

    @Inject
    ResponseFacade responseFacade;

    @Then("^message body is empty$")
    public void assertMessageBodyIsEmpty() {
        throw new NotImplementedException("Not yet implemented");
    }

    @Then("^message body is not empty$")
    public void assertMessageBodyIsNotEmpty() {
        throw new NotImplementedException("Not yet implemented");
    }

    @Then("^message body equals to \"(.*)\"$")
    public void assertMessageBodyEqualsTo(String body) {
        responseFacade.assertMessageBodyEqualsTo(body);
    }

    @Then("^message body not equal to \"(.*)\"$")
    public void assertMessageBodyDoesNotEqualTo(String body) {
        throw new NotImplementedException("Not yet implemented");
    }

    @Then("^message body contains \"(.+)\"$")
    public void assertMessageBodyContains(String body) {
        throw new NotImplementedException("Not yet implemented");
    }

    @Then("^message body does not contain \"(.+)\"$")
    public void assertMessageBodyDoesNotContain(String body) {
            throw new NotImplementedException("Not yet implemented");
    }

    @Then("^message body contains property \"(.+)\" containing phrase \"(.*)\"$")
    public void assertMessageContainsPropertyWithPhrase(String path, String phrase) {
        responseFacade.assertMessageContainsPropertyWithPhrase(path, phrase);
    }

    @Then("^message body contains property \"(.+)\" with value \"(.*)\"$")
    public void assertMessageContainsPropertyWithValue(String path, String value) {
        responseFacade.assertMessageContainsPropertyWithValue(path, value);
    }

}
