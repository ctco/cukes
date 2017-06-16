package lv.ctco.cukes.rest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.core.internal.resources.ResourceFileReader;
import lv.ctco.cukes.rest.facade.RestAssertionFacade;

@Singleton
public class ThenSteps {

    @Inject
    private RestAssertionFacade assertionFacade;

    @Inject
    private ResourceFileReader fileReader;

    @Then("^let variable \"(.+)\" equal to header \"(.+)\" value$")
    public void var_assigned_from_header(String varName, String headerName) {
        this.assertionFacade.varAssignedFromHeader(varName, headerName);
    }

    @Then("^let variable \"(.+)\" equal to property \"(.+)\" value$")
    public void var_assigned_fromProperty(String varName, String property) {
        this.assertionFacade.varAssignedFromProperty(varName, property);
    }

    @Then("^let variable \"(.+)\" equal to response body")
    public void var_assigned_fromBody(String varName) {
        this.assertionFacade.varAssignedFromBody(varName);
    }

    @Then("^response is empty$")
    public void response_Body_Is_Empty() {
        this.assertionFacade.bodyEqualTo("");
    }

    @Then("^response is not empty$")
    public void response_Body_Is_Not_Empty() {
        this.assertionFacade.bodyNotEmpty();
    }

    @Then("^response equals to \"(.*)\"$")
    public void response_Body_Equal_To(String body) {
        this.assertionFacade.bodyEqualTo(body);
    }

    @Then("^response body not equal to \"(.*)\"$")
    public void response_Body_Not_Equal_To(String body) {
        this.assertionFacade.bodyNotEqualTo(body);
    }

    @Then("^response contains \"(.+)\"$")
    public void response_Body_Contains(String body) {
        this.assertionFacade.bodyContains(body);
    }

    @Then("^response body does not contain \"(.+)\"$")
    public void response_Body_Does_Not_Contain(String body) {
        this.assertionFacade.bodyDoesNotContain(body);
    }

    @Then("^response contains property \"(.+)\" containing phrase \"(.*)\"$")
    public void response_Body_Contains_Property_With_Phrase(String jsonPath, String phrase) {
        this.assertionFacade.bodyContainsJsonPathValueContainingPhrase(jsonPath, phrase);
    }

    @Then("^response contains property \"(.+)\" with value \"(.*)\"$")
    public void response_Body_Contains_Property(String path, String value) {
        this.assertionFacade.bodyContainsPathWithValue(path, value);
    }

    @Then("^response contains property \"(.+)\" with value:$")
    public void response_Body_Contains_Property_Multiline(String path, String value) {
        this.assertionFacade.bodyContainsPathWithValue(path, value);
    }

    @Then("^response contains property \"(.+)\" with value other than \"(.*)\"$")
    public void response_Body_Contains_Property_Other_Value(String path, String value) {
        this.assertionFacade.bodyContainsPathWithOtherValue(path, value);
    }

    @Then("^response contains property \"(.+)\" of type \"(.+)\"$")
    public void response_Body_Contains_Property_Of_Type(String path, String type) {
        this.assertionFacade.bodyContainsPathOfType(path, type);
    }

    @Then("^response contains an array \"(.+)\" of size (>=|>|<=|<|<>) (\\d+)$")
    public void response_Body_Contains_Array_With_Operator_Size(String path, String operator, Integer size) {
        this.assertionFacade.bodyContainsArrayWithSize(path, operator + size);
    }

    @Then("^response contains an array \"(.+)\" of size (\\d+)$")
    public void response_Body_Contains_Array_With_Size(String path, Integer size) {
        this.assertionFacade.bodyContainsArrayWithSize(path, size.toString());
    }

    @Then("^response contains an array \"(.+)\" with value \"(.*)\"$")
    public void response_Body_Contains_Array_With_Property(String path, String value) {
        this.assertionFacade.bodyContainsArrayWithEntryHavingValue(path, value);
    }

    @Then("^response does not contain property \"(.+)\"")
    public void response_Body_Does_Not_Contain_Property(String path) {
        this.assertionFacade.bodyDoesNotContainPath(path);
    }

    @Then("^response contains property \"(.+)\" matching pattern \"(.+)\"$")
    public void response_Body_Contains_Property_Matching_Pattern(String path, String pattern) {
        this.assertionFacade.bodyContainsPathMatchingPattern(path, pattern);
    }

    @Then("^response contains property \"(.+)\" not matching pattern \"(.+)\"$")
    public void response_Body_Contains_Property_Not_Matching_Pattern(String path, String pattern) {
        this.assertionFacade.bodyContainsPathNotMatchingPattern(path, pattern);
    }

    @Then("^response contains properties from file \"(.+)\"$")
    public void response_Body_Contains_Properties_From_File(String path) {
        this.assertionFacade.bodyContainsPropertiesFromJson(this.fileReader.read(path));
    }

    @Then("^response contains properties from json:$")
    public void response_Body_Contains_Properties_From(String str) {
        this.assertionFacade.bodyContainsPropertiesFromJson(str);
    }

    @Then("^status code is (\\d+)$")
    public void response_Status_Code_Is(int statusCode) {
        this.assertionFacade.statusCodeIs(statusCode);
    }

    @Then("^status code is not (\\d+)$")
    public void response_Status_Code_Is_Not(int statusCode) {
        this.assertionFacade.statusCodeIsNot(statusCode);
    }

    @Then("^header \"(.+)\" is empty$")
    public void header_Is_Empty(String headerName) {
        this.assertionFacade.headerIsEmpty(headerName);
    }

    @Then("^header \"(.+)\" is not empty$")
    public void header_Is_Not_Empty(String headerName) {
        this.assertionFacade.headerIsNotEmpty(headerName);
    }

    @Then("^header \"(.+)\" equal to \"(.+)\"$")
    public void header_Equal_To(String headerName, String value) {
        this.assertionFacade.headerEqualTo(headerName, value);
    }

    @Then("^header \"(.+)\" not equal to \"(.+)\"$")
    public void header_Not_Equal_To(String headerName, String value) {
        this.assertionFacade.headerNotEqualTo(headerName, value);
    }

    @Then("^header \"(.+)\" contains \"(.+)\"$")
    public void header_Contains(String headerName, String text) {
        this.assertionFacade.headerContains(headerName, text);
    }

    @Then("^header \"(.+)\" does not contain \"(.+)\"$")
    public void header_Does_Not_Contain(String headerName, String text) {
        this.assertionFacade.headerDoesNotContain(headerName, text);
    }

    @Then("^header \"(.+)\" ends with pattern \"(.+)\"$")
    public void header_Ends_With(String headerName, String suffix) {
        this.assertionFacade.headerEndsWith(headerName, suffix);
    }

    @Then("a failure is expected")
    public void a_failure_is_expected() {
        this.assertionFacade.failureIsExpected();
    }

    @Then("^it fails with \"(.+)\"$")
    public void it_fails(String exceptionClass) {
        this.assertionFacade.failureOccurs(exceptionClass);
    }
}
