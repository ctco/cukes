package lv.ctco.cukes.graphql.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.graphql.facade.GQLAssertionFacade;

@Singleton
public class ThenSteps {

    @Inject
    private GQLAssertionFacade assertionFacade;

    @Then("^response contains property \"(.+)\" with value \"(.*)\"$")
    public void responseBodyContainsProperty(String path, String value) {
        this.assertionFacade.responseContainsPropertyWithValue(path, value);
    }

    @Then("^response contains an array \"(.+)\" of size (>=|>|<=|<|<>) (\\d+)$")
    public void responseBodyContainsArrayWithOperatorSize(String path, String operator, Integer size) {
        this.assertionFacade.bodyContainsArrayWithSize(path, operator + size);
    }

    @Then("^response contains an array \"(.+)\" of size (\\d+)$")
    public void responseBodyContainsArrayWithSize(String path, Integer size) {
        this.assertionFacade.bodyContainsArrayWithSize(path, size.toString());
    }

    @And("^response contains an array \"(.+)\" with object having property \"(.+)\" with value \"(.+)\"$")
    public void responseBodyContainsArrayWithObjectHavingProperty(String path, String property, String value) {
        this.assertionFacade.bodyContainsArrayWithObjectHavingProperty(path, property, value);
    }
}
