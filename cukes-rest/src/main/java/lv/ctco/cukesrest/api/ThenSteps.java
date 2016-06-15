package lv.ctco.cukesrest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lv.ctco.cukesrest.internal.AssertionFacade;
import lv.ctco.cukesrest.internal.resources.ResourceFileReader;

@Singleton
public class ThenSteps {

    @Inject
    private AssertionFacade assertionFacade;

    @Inject
    private ResourceFileReader fileReader;

    @Then("^let variable \"([^\"]+)\" equal to header \"([^\"]+)\" value$")
    public void var_assigned_from_header(String varName, String headerName) {
        assertionFacade.varAssignedFromHeader(varName, headerName);
    }

    @Then("^let variable \"([^\"]+)\" equal to property \"([^\"]+)\" value$")
    public void var_assigned_fromProperty(String varName, String property) {
        assertionFacade.varAssignedFromProperty(varName, property);
    }

    @Then("^response is empty$")
    public void response_Body_Is_Empty() {
        assertionFacade.bodyEqualTo("");
    }

    @Then("^response is not empty$")
    public void response_Body_Is_Not_Empty() {
        assertionFacade.bodyNotEmpty();
    }

    @Then("^response equals to \"(.*)\"$")
    public void response_Body_Equal_To(String body) {
        assertionFacade.bodyEqualTo(body);
    }

    @Then("^response body not equal to \"(.*)\"$")
    public void response_Body_Not_Equal_To(String body) {
        assertionFacade.bodyNotEqualTo(body);
    }

    @Then("^response contains \"(.+)\"$")
    public void response_Body_Contains(String body) {
        assertionFacade.bodyContains(body);
    }

    @Then("^response body does not contain \"(.+)\"$")
    public void response_Body_Does_Not_Contain(String body) {
        assertionFacade.bodyDoesNotContain(body);
    }

    @Then("^response contains property \"(.+)\" containing phrase \"(.*)\"$")
    public void response_Body_Contains_Property_With_Phrase(String jsonPath, String phrase) {
        assertionFacade.bodyContainsJsonPathValueContainingPhrase(jsonPath, phrase);
    }

    @Then("^response contains property \"(.+)\" with value \"(.*)\"$")
    public void response_Body_Contains_Property(String path, String value) {
        assertionFacade.bodyContainsPathWithValue(path, value);
    }

    @Then("^response contains property \"(.+)\" with value other than \"(.*)\"$")
    public void response_Body_Contains_Property_Other_Value(String path, String value) {
        assertionFacade.bodyContainsPathWithOtherValue(path, value);
    }

    @Then("^response contains property \"(.+)\" of type \"(.+)\"$")
    public void response_Body_Contains_Property_Of_Type(String path, String type) {
        assertionFacade.bodyContainsPathOfType(path, type);
    }

    @Then("^response contains an array \"(.+)\" of size \"(.+)\"$")
    public void response_Body_Contains_Array_With_Size(String path, String size) {
        assertionFacade.bodyContainsArrayWithSize(path, size);
    }

    @Then("^response does not contain property \"(.+)\"")
    public void response_Body_Does_Not_Contain_Property(String path) {
        assertionFacade.bodyDoesNotContainPath(path);
    }

    @Then("^response contains property \"(.+)\" matching pattern \"(.+)\"$")
    public void response_Body_Contains_Property_Matching_Pattern(String path, String pattern) {
        assertionFacade.bodyContainsPathMatchingPattern(path, pattern);
    }

    @Then("^response contains property \"(.+)\" not matching pattern \"(.+)\"$")
    public void response_Body_Contains_Property_Not_Matching_Pattern(String path, String pattern) {
        assertionFacade.bodyContainsPathNotMatchingPattern(path, pattern);
    }

    @Then("^response contains properties from file (.+)$")
    public void response_Body_Contains_Properties_From_File(String path) {
        assertionFacade.bodyContainsPropertiesFromJson(fileReader.read(path));
    }

    @Then("^response contains properties from json")
    public void response_Body_Contains_Properties_From(String str) {
        assertionFacade.bodyContainsPropertiesFromJson(str);
    }

    @Then("^status code is (\\d+)$")
    public void response_Status_Code_Is(int statusCode) {
        assertionFacade.statusCodeIs(statusCode);
    }

    @Then("^status code is not (\\d+)$")
    public void response_Status_Code_Is_Not(int statusCode) {
        assertionFacade.statusCodeIsNot(statusCode);
    }

    @Then("^header (.+) is empty$")
    public void header_Is_Empty(String headerName) {
        assertionFacade.headerIsEmpty(headerName);
    }

    @Then("^header (.+) is not empty$")
    public void header_Is_Not_Empty(String headerName) {
        assertionFacade.headerIsNotEmpty(headerName);
    }

    @Then("^header (.+) equal to \"(.+)\"$")
    public void header_Equal_To(String headerName, String value) {
        assertionFacade.headerEqualTo(headerName, value);
    }

    @Then("^header (.+) not equal to \"(.+)\"$")
    public void header_Not_Equal_To(String headerName, String value) {
        assertionFacade.headerNotEqualTo(headerName, value);
    }

    @Then("^header (.+) contains \"(.+)\"$")
    public void header_Contains(String headerName, String text) {
        assertionFacade.headerContains(headerName, text);
    }

    @Then("^header (.+) does not contain \"(.+)\"$")
    public void header_Does_Not_Contain(String headerName, String text) {
        assertionFacade.headerDoesNotContain(headerName, text);
    }

    @Then("^header (.+) ends with pattern \"(.+)\"$")
    public void header_Ends_With(String headerName, String suffix) {
        assertionFacade.headerEndsWith(headerName, suffix);
    }

    @Then("a failure is expected")
    public void a_failure_is_expected() {
        assertionFacade.failureIsExpected();
    }
}
