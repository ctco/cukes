package lv.ctco.cukes.ldap.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.ldap.facade.EntityFacade;

@Singleton
@InflateContext
public class ThenSteps {

    @Inject
    EntityFacade entityFacade;

    @Then("^entity exists$")
    public void entityExists() {
        entityFacade.entityExists();
    }

    @Then("^entity does not exist$")
    public void entityDoesNotExist() {
        entityFacade.entityDoesNotExist();
    }

    @Then("^entity contains attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\"$")
    public void entityContainsAttribute(String attribute) {
        entityFacade.entityContainsAttribute(attribute);
    }

    @Then("^entity does not contain attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\"$")
    public void entityDoesNotContainAttribute(String attribute) {
        entityFacade.entityDoesNotContainAttribute(attribute);
    }

    @Then("^entity contains attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\" with value \"(.*)\"$")
    public void entityHasAttributeWithValue(String attribute, String value) {
        entityFacade.entityHasAttributeWithValue(attribute, value);
    }

    @Then("^entity contains attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\" with value other than \"(.*)\"$")
    public void entityHasAttributeWithValueOtherThat(String attribute, String value) {
        entityFacade.entityHasAttributeWithValueOtherThat(attribute, value);
    }

    @Then("^entity contains attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\" with (\\d+) values?$")
    public void entityHasAttributeAsArrayOfSize(String attribute, int size) {
        entityFacade.entityHasAttributeAsArrayOfSize(attribute, "=", size);
    }

    @Then("^entity contains attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\" with (>=|>|<=|<|<>) (\\d+) values?$")
    public void entityHasAttributeAsArrayOfSize(String attribute, String operator, int size) {
        entityFacade.entityHasAttributeAsArrayOfSize(attribute, operator, size);
    }

    @Then("^entity contains attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\" matching pattern \"(.+)\"$")
    public void entityHasAttributeWithValueMatchingPattern(String attribute, String pattern) {
        entityFacade.entityHasAttributeWithValueMatchingPattern(attribute, pattern);
    }

    @Then("^entity contains attribute \"([a-zA-Z][a-zA-Z0-9\\-]*)\" not matching pattern \"(.+)\"$")
    public void entityHasAttributeWithValueNotMatchingPattern(String attribute, String pattern) {
        entityFacade.entityHasAttributeWithValueNotMatchingPattern(attribute, pattern);
    }

    @Then("^entity matches LDIF:$")
    public void entityMatchesLDIF(String ldif) {
        entityFacade.entityMatchesLDIF(ldif);
    }

    @Then("^search result has size (\\d+)$")
    public void searchResultHasSize(int size) {
        entityFacade.searchResultHasSize("=", size);
    }

    @Then("^search result has size (>=|>|<=|<|<>) (\\d+)$")
    public void searchResultHasSize(String operator, int size) {
        entityFacade.searchResultHasSize(operator, size);
    }

    @Then("^take entity with index (\\d+) from search results$")
    public void takeEntityFromSearchResults(int index) {
        entityFacade.takeEntityFromSearchResults(index - 1);
    }
}
