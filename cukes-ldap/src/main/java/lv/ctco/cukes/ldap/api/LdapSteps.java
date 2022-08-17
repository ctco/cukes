package lv.ctco.cukes.ldap.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.core.CukesDocs;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.ldap.facade.EntityFacade;
import lv.ctco.cukes.ldap.facade.ModificationFacade;

import java.util.List;
import java.util.Map;

@Singleton
@InflateContext
public class LdapSteps {
    @Inject
    private EntityFacade entityFacade;
    @Inject
    private ModificationFacade modificationFacade;
    @Inject
    private VariableFacade variableFacade;

    //CREATE
    @And("^LDAP entity is created using LDIF:$")
    @CukesDocs("Creates LDAP entity from LDIF body")
    public void importLdif(String ldif) {
        entityFacade.importLdif(ldif);
    }

    @And("^LDAP entity is created using LDIF from file \"([^\"]+)\"$")
    @CukesDocs("Creates LDAP entity from separate LDIF file")
    public void createEntityFromLdifFile(String ldifFile) {
        entityFacade.importLdifFromFile(ldifFile);
    }

    //READ
    @And("^LDAP entity by DN \"([^\"]+)\" is retrieved$")
    @CukesDocs("Gets LDAP entity for further processing")
    public void readEntityByDn(String dn) {
        entityFacade.readEntityByDn(dn);
    }

    @And("^LDAP entities are searched within DN \"([^\"]+)\" by filter \"([^\"]+)\"$")
    @CukesDocs("Gets LDAP entities search results for further processing")
    public void searchEntitiesByFilter(String dn, String filter) {
        entityFacade.searchByFilter(dn, filter);
    }

    @And("^LDAP entity with index (\\d+) from search results is retrieved$")
    @CukesDocs("Gets LDAP entities search results for further processing")
    public void readEntityFromSearchResults(int index) {
        entityFacade.takeEntityFromSearchResults(index - 1);
    }

    @And("^LDAP entity by DN \"([^\"]+)\" attribute \"([^\"]+)\" value is saved to variable \"([^\"]+)\"$")
    @CukesDocs("Creates variables with LDAP entity attribute value")
    public void createVariableSetToLdapEntityAttributeValue(String dn, String attribute, String varName) throws Throwable {
        entityFacade.readEntityByDn(dn);
        String value = entityFacade.getNotNullAttribute(attribute).get().toString();
        this.variableFacade.setVariable(varName, value);
    }

    //UPDATE
    @And("^LDAP entity new modifications are prepared$")
    @CukesDocs("Clears previous ldap entity modifications. " +
        "First, you need to retrieve entity itself before this step.")
    public void prepareNewModification() {
        modificationFacade.reset();
    }

    @And("^LDAP entity attribute \"([^\"]+)\" value \"([^\"]+)\" is modified with operation \"(add|remove|replace)\"$")
    @CukesDocs("Modifies LDAP entity attribute by adding value, replacing value or removing it. " +
        "First, you need to retrieve entity itself before this step. " +
        "Second, you need to update entity after this step.")
    public void addModification(String attribute, String operation, String value) {
        modificationFacade.add(attribute, operation, value);
    }

    @And("^LDAP entity by DN \"([^\"]+)\" is updated using prepared modifications$")
    @CukesDocs("Updates LDAP entity with prepared modifications. " +
        "You need to retrieve and make entity modification before this step")
    public void modifyEntityWithDn(String dn) {
        modificationFacade.execute(dn);
    }

    @And("^LDAP entity by DN \"([^\"]+)\" attribute \"([^\"]+)\" value \"([^\"]*)\" is modified with operation \"(add|remove|replace)\"$")
    @CukesDocs("Updates LDAP entity attribute value with selected modication: add, remove or replace.")
    public void updateEntityInLDAP(String dn, String attributeName, String attributeValue, String operation) {
        modificationFacade.reset();
        modificationFacade.add(attributeName, operation, attributeValue);
        modificationFacade.execute(dn);
    }

    @And("^LDAP entity by DN \"([^\"]+)\" attributes are updated:$")
    @CukesDocs("Updates LDAP entity with set of modications. Allowed operations: add, remove, replace.")
    public void updateEntityInLDAP(String dn, List<Map<String, String>> attributeList) {
        modificationFacade.reset();
        attributeList.forEach(attributeMap -> {
            String attributeName = attributeMap.get("attribute");
            String attributeValue = attributeMap.get("value");
            String operation = attributeMap.get("operation") != null ? attributeMap.get("operation") : "replace";
            modificationFacade.add(attributeName, operation, attributeValue);
        });
        modificationFacade.execute(dn);
    }

    //DELETE
    @And("^LDAP entity by DN \"([^\"]+)\" is deleted$")
    @CukesDocs("Removes LDAP entity")
    public void deleteEntityWithDn(String dn) {
        entityFacade.deleteEntityByDn(dn);
    }

    //ASSERT
    @Then("^LDAP entity should( not|) exist$")
    @CukesDocs("Asserts that preset LDAP entity exists or not. " +
        "First, you need to retrieve entity itself before this step.")
    public void checkEntityExists(String condition) {
        boolean shouldExist = condition.isEmpty();
        if (shouldExist) {
            entityFacade.entityExists();
        } else {
            entityFacade.entityDoesNotExist();
        }
    }

    @Then("^LDAP entity by DN \"([^\"]+)\" should( not|) exist$")
    @CukesDocs("Asserts that LDAP entity exists or not.")
    public void checkEntityByDnExists(String dn, String condition) {
        entityFacade.readEntityByDn(dn);
        checkEntityExists(condition);
    }

    @Then("^LDAP entity should( not|) contain attribute \"([^\"]+)\"$")
    @CukesDocs("Asserts that preset LDAP entity contains or not contains attribute. " +
        "First, you need to retrieve entity itself before this step.")
    public void checkEntityContainsAttribute(String condition, String attribute) {
        boolean shouldContain = condition.isEmpty();
        if (shouldContain) {
            entityFacade.entityContainsAttribute(attribute);
        } else {
            entityFacade.entityDoesNotContainAttribute(attribute);
        }
    }

    @Then("^LDAP entity by DN \"([^\"]+)\" should( not|) contain attribute \"([^\"]+)\"$")
    @CukesDocs("Asserts that LDAP entity contains or not contains attribute.")
    public void checkEntityByDnContainsAttribute(String dn, String condition, String attribute) {
        entityFacade.readEntityByDn(dn);
        checkEntityContainsAttribute(condition, attribute);
    }

    @Then("^LDAP entity attribute \"([^\"]+)\" value should( not|) be equal to \"([^\"]*)\"$")
    @CukesDocs("Asserts that preset LDAP entity attribute value is equal or not equal to certain value. " +
        "First, you need to retrieve entity itself before this step.")
    public void checkEntityHasAttributeWithValue(String attribute, String condition, String value) {
        boolean shouldBeEqual = condition.isEmpty();
        if (shouldBeEqual) {
            entityFacade.entityHasAttributeWithValue(attribute, value);
        } else {
            entityFacade.entityHasAttributeWithValueOtherThat(attribute, value);
        }
    }

    @Then("^LDAP entity by DN \"([^\"]+)\" attribute \"([^\"]+)\" value should( not|) be equal to \"([^\"]*)\"$")
    @CukesDocs("Asserts that LDAP entity attribute value is equal or not equal to certain value.")
    public void checkEntityByDnHasAttributeWithValue(String dn, String attribute, String condition, String value) {
        entityFacade.readEntityByDn(dn);
        checkEntityHasAttributeWithValue(attribute, condition, value);
    }

    @Then("^LDAP entity attribute \"([^\"]+)\" should( not|) contain (\\d+) values?$")
    @CukesDocs("Asserts that preset LDAP entity attribute should contain or not contain certain number of values. " +
        "First, you need to retrieve entity itself before this step.")
    public void checkEntityHasAttributeWithValueArray(String attribute, String condition, int size) {
        boolean shouldContain = condition.isEmpty();
        if (shouldContain) {
            entityFacade.entityHasAttributeAsArrayOfSize(attribute, "=", size);
        } else {
            entityFacade.entityHasAttributeAsArrayOfSize(attribute, "<>", size);
        }
    }

    @Then("^LDAP entity by DN \"([^\"]+)\" attribute \"([^\"]+)\" should( not|) contain (\\d+) values?$")
    @CukesDocs("Asserts that LDAP entity attribute should contain or not contain certain number of values.")
    public void checkEntityByDnHasAttributeWithValueArray(String dn, String attribute, String condition, int size) {
        entityFacade.readEntityByDn(dn);
        checkEntityHasAttributeWithValueArray(attribute, condition, size);
    }

    @Then("^LDAP entity attribute \"([^\"]+)\" should contain (>=|>|<=|<) than (\\d+) values?$")
    @CukesDocs("Asserts that preset LDAP entity attribute should contain more or equal, more, less or equal, less than certain number of values. " +
        "First, you need to retrieve entity itself before this step.")
    public void checkEntityHasAttributeAsArrayOfSize(String attribute, String operator, int size) {
        entityFacade.entityHasAttributeAsArrayOfSize(attribute, operator, size);
    }

    @Then("^LDAP entity by DN \"([^\"]+)\" attribute \"([^\"]+)\" should contain (>=|>|<=|<) than (\\d+) values?$")
    @CukesDocs("Asserts that LDAP entity attribute should contain more or equal, more, less or equal, less than certain number of values.")
    public void checkEntityByDnHasAttributeAsArrayOfSize(String dn, String attribute, String operator, int size) {
        entityFacade.readEntityByDn(dn);
        entityFacade.entityHasAttributeAsArrayOfSize(attribute, operator, size);
    }

    @Then("^LDAP entity attribute \"([^\"]+)\" should( not|) match pattern \"([^\"]+)\"$")
    @CukesDocs("Asserts that preset LDAP entity attribute should match or not match certain pattern. " +
        "First, you need to retrieve entity itself before this step.")
    public void checkEntityHasAttributeWithValueMatchingPattern(String attribute, String condition, String pattern) {
        boolean shouldMatch = condition.isEmpty();
        if (shouldMatch) {
            entityFacade.entityHasAttributeWithValueMatchingPattern(attribute, pattern);
        } else {
            entityFacade.entityHasAttributeWithValueNotMatchingPattern(attribute, pattern);
        }
    }

    @Then("^LDAP entity by DN \"([^\"]+)\" attribute \"([^\"]+)\" should( not|) match pattern \"([^\"]+)\"$")
    @CukesDocs("Asserts that LDAP entity attribute should match or not match certain pattern.")
    public void checkEntityBeDnHasAttributeWithValueMatchingPattern(String dn, String attribute, String condition, String pattern) {
        entityFacade.readEntityByDn(dn);
        checkEntityHasAttributeWithValueMatchingPattern(attribute, condition, pattern);
    }

    @Then("^LDAP entity should match LDIF:$")
    @CukesDocs("Asserts that preset LDAP entity attribute should match certain LDIF. " +
        "First, you need to retrieve entity itself before this step.")
    public void checkEntityMatchesLDIF(String ldif) {
        entityFacade.entityMatchesLDIF(ldif);
    }

    @Then("^LDAP entity by DN \"([^\"]+)\" should match LDIF:$")
    @CukesDocs("Asserts that LDAP entity attribute should match certain LDIF.")
    public void checkEntityByDnMatchesLDIF(String dn, String ldif) {
        entityFacade.readEntityByDn(dn);
        entityFacade.entityMatchesLDIF(ldif);
    }

    @Then("^LDAP entities search result size should( not|) be equal to (\\d+)$")
    @CukesDocs("Asserts that prefound LDAP entity search result size should be equal or not be equal to certain number. " +
        "First, you need to search for LDAP entities before this step.")
    public void checkSearchResultSizeEquals(String condition, int size) {
        boolean shouldBeEqual = condition.isEmpty();
        if (shouldBeEqual) {
            entityFacade.searchResultHasSize("=", size);
        } else {
            entityFacade.searchResultHasSize("<>", size);
        }
    }

    @Then("^LDAP entities search within DN \"([^\"]+)\" by filter \"([^\"]+)\" result size should( not|) be equal to (\\d+)$")
    @CukesDocs("Asserts that LDAP entity search result size should be equal or not be equal to certain number.")
    public void checkEntitiesByDnSearchResultSizeEquals(String dn, String filter, String condition, int size) {
        entityFacade.searchByFilter(dn, filter);
        checkSearchResultSizeEquals(condition, size);
    }

    @Then("^LDAP entities search result size should be (>=|>|<=|<) than (\\d+)$")
    @CukesDocs("Asserts that prefound LDAP entity search result size should be more or equal, more, less or equal, less than certain number. " +
        "First, you need to search for LDAP entities before this step.")
    public void checkEntitiesSearchResultSizeDiffers(String operator, int size) {
        entityFacade.searchResultHasSize(operator, size);
    }

    @Then("^LDAP entities search within DN \"([^\"]+)\" by filter \"([^\"]+)\" result size should be (>=|>|<=|<) than (\\d+)$")
    @CukesDocs("Asserts that LDAP entity search result size should be more or equal, more, less or equal, less than certain number.")
    public void checkEntitiesByDnSearchResultSizeDiffers(String dn, String filter, String operator, int size) {
        entityFacade.searchByFilter(dn, filter);
        checkEntitiesSearchResultSizeDiffers(operator, size);
    }
}
