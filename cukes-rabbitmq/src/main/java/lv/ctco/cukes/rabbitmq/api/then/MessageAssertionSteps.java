package lv.ctco.cukes.rabbitmq.api.then;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.rabbitmq.facade.ResponseFacade;
import org.apache.commons.lang3.NotImplementedException;

@Singleton
@InflateContext
public class MessageAssertionSteps {

    @Inject
    ResponseFacade responseFacade;

    @Then("^let variable \"(.+)\" equal to message body")
    public void variableAssignedFromMessageBody(String varName) {
        throw new NotImplementedException("Not yet implemented");
    }

    @Then("^message body is empty$")
    public void assertMessageBodyIsEmpty() {
        responseFacade.assertMessageBodyIsEmpty();
    }

    @Then("^message body is not empty$")
    public void assertMessageBodyIsNotEmpty() {
        responseFacade.assertMessageBodyIsNotEmpty();
    }

    @Then("^message body equals to \"(.*)\"$")
    public void assertMessageBodyEqualsTo(String body) {
        responseFacade.assertMessageBodyEqualsTo(body);
    }

    @Then("^message body not equal to \"(.*)\"$")
    public void assertMessageBodyDoesNotEqualTo(String body) {
        responseFacade.assertMessageBodyDoesNotEqualTo(body);
    }

    @Then("^message body contains \"(.+)\"$")
    public void assertMessageBodyContains(String body) {
        responseFacade.assertMessageBodyContains(body);
    }

    @Then("^message body does not contain \"(.+)\"$")
    public void assertMessageBodyDoesNotContain(String body) {
        responseFacade.assertMessageBodyDoesNotContain(body);
    }

    @Then("^message body contains property \"(.+)\" containing phrase \"(.*)\"$")
    public void assertMessageContainsPropertyWithPhrase(String path, String phrase) {
        responseFacade.assertMessageContainsPropertyWithPhrase(path, phrase);
    }

    @Then("^message body contains property \"(.+)\" with value \"(.*)\"$")
    public void assertMessageContainsPropertyWithValue(String path, String value) {
        responseFacade.assertMessageContainsPropertyWithValue(path, value);
    }

    @Then("^message body contains property \"(.+)\" with value other than \"(.*)\"$")
    public void assertMessageBodyContainsPathWithOtherValue(String path, String value) {
        responseFacade.assertMessageBodyContainsPathWithOtherValue(path, value);
    }

    @Then("^message body contains property \"(.+)\" of type \"(.+)\"$")
    public void assertMessageBodyContainsPathOfType(String path, String type) {
        responseFacade.assertMessageBodyContainsPathOfType(path, type);
    }

    @Then("^message body contains an array \"(.+)\" of size (>=|>|<=|<|<>) (\\d+)$")
    public void assertMessageBodyContainsArrayWithSize(String path, String operator, Integer size) {
        responseFacade.assertMessageBodyContainsArrayWithSize(path, operator, size);
    }

    @Then("^message body contains an array \"(.+)\" of size (\\d+)$")
    public void assertMessageBodyContainsArrayWithSize(String path, Integer size) {
        responseFacade.assertMessageBodyContainsArrayWithSize(path, size);
    }

    @Then("^message body contains an array \"(.+)\" with value \"(.*)\"$")
    public void assertMessageBodyContainsArrayWithEntryHavingValue(String path, String value) {
        responseFacade.assertMessageBodyContainsArrayWithEntryHavingValue(path, value);
    }

    @Then("^message body does not contain property \"(.+)\"")
    public void assertMessageBodyDoesNotContainPath(String path) {
        responseFacade.assertMessageBodyDoesNotContainPath(path);
    }

    @Then("^message body contains property \"(.+)\" matching pattern \"(.+)\"$")
    public void assertMessageBodyContainsPathMatchingPattern(String path, String pattern) {
        responseFacade.assertMessageBodyContainsPathMatchingPattern(path, pattern);
    }

    @Then("^message body contains property \"(.+)\" not matching pattern \"(.+)\"$")
    public void assertMessageBodyContainsPathNotMatchingPattern(String path, String pattern) {
        responseFacade.assertMessageBodyContainsPathNotMatchingPattern(path, pattern);
    }

}
