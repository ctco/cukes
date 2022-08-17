package lv.ctco.cukes.http.api;

import com.google.inject.Inject;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import groovy.lang.Singleton;
import groovy.util.logging.Slf4j;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lv.ctco.cukes.core.CukesDocs;
import lv.ctco.cukes.core.internal.context.ContextInflater;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.http.facade.HttpAssertionFacade;
import lv.ctco.cukes.http.facade.HttpRequestFacade;
import lv.ctco.cukes.http.facade.HttpResponseFacade;
import lv.ctco.cukes.http.json.JsonParser;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

@Singleton
@Slf4j
@InflateContext
public class HttpSteps {

    @Inject
    private HttpRequestFacade httpRequestFacade;
    @Inject
    private HttpResponseFacade httpResponseFacade;
    @Inject
    private HttpAssertionFacade httpAssertionFacade;
    @Inject
    private JsonParser jsonParser;
    @Inject
    private ContextInflater inflater;
    @Inject
    private GlobalWorldFacade world;


    @And("^HTTP response (header|property) \"([^\"]+)\" value is saved to variable \"([^\"]+)\"$")
    @CukesDocs("Save response property or header value to variable")
    public void saveHttpResponseHeaderToAVariable(String headerOrProperty, String attributeName, String varName) {
        if (headerOrProperty.equals("header")) {
            httpAssertionFacade.varAssignedFromHeader(varName, attributeName);
        } else {
            httpAssertionFacade.varAssignedFromProperty(varName, attributeName);
        }
    }

    @And("^variable \"([^\"]+)\" is HTTP response (header|property) \"([^\"]+)\" value$")
    @CukesDocs("Save response property or header value to variable")
    public void setVariableToHttpResponseHeaderValue(String varName, String headerOrProperty, String attributeName) {
        if (headerOrProperty.equals("header")) {
            httpAssertionFacade.varAssignedFromHeader(varName, attributeName);
        } else {
            httpAssertionFacade.varAssignedFromProperty(varName, attributeName);
        }
    }

    @And("^HTTP content type is \"([^\"]+)\"$")
    @CukesDocs("Setup content type for http request")
    public void setContentType(String contentType) {
        httpRequestFacade.contentType(contentType);
    }

    @And("^HTTP content type is (ANY|TEXT|JSON|XML|HTML|URLENC|BINARY)$")
    @CukesDocs("Select content type for http request")
    public void setContentTypeJson(ContentType contentType) {
        httpRequestFacade.contentType(contentType.toString());
    }

    @And("^HTTP \"(GET|POST|PUT|DELETE|OPTIONS|HEAD|PATCH)\" request is sent to \"(.+)\"$")
    @CukesDocs("Send http request")
    public void performHttpRequest(String httpMethod, String url) throws Throwable {
        httpResponseFacade.setResponsePrefix("");
        httpResponseFacade.doRequest(httpMethod, url);
    }

    //ASSERT
    @Then("^HTTP response status code should( not|) be (\\d+)$")
    @CukesDocs("Check http response status code")
    public void checkHttpRsponseStatusCode(String condition, int expectedStatusCode) {
        int actualStatusCode = httpResponseFacade.response().statusCode();
        boolean shouldBeEqual = condition.isEmpty();
        boolean isEqual = actualStatusCode == expectedStatusCode;
        Assert.assertTrue(
            "Status code should" + condition + " be " + expectedStatusCode + " but was " + actualStatusCode +
                "\nAnd response body is:\n" + httpResponseFacade.response().body().prettyPrint(),
            isEqual == shouldBeEqual);
    }

    @Then("^HTTP response property \"([^\"]+)\" value should( not|) be equal to \"([^\"]*)\"$")
    @CukesDocs("Check http response property")
    public void checkHttpResponsePropertyEquals(String path, String condition, String value) {
        boolean shouldBeEqual = condition.isEmpty();
        if (shouldBeEqual) {
            httpAssertionFacade.bodyContainsPathWithValue(path, value);
        } else {
            httpAssertionFacade.bodyContainsPathWithOtherValue(path, value);
        }
    }

    @Then("^HTTP response header \"([^\"]+)\" value should( not|) be equal to \"([^\"]+)\"$")
    @CukesDocs("Check http response header")
    public void checkHttpResponseHeaderEquals(String headerName, String condition, String value) {
        boolean shouldBeEqual = condition.isEmpty();
        if (shouldBeEqual) {
            httpAssertionFacade.headerContains(headerName, value);
        } else {
            httpAssertionFacade.headerDoesNotContain(headerName, value);
        }
    }

    @Then("^HTTP response should contain properties from json:$")
    @CukesDocs("Check http response contains properties from certain json")
    public void checkHttpResponseContainsProperty(String json) {
//        httpAssertionFacade.bodyContainsPropertiesFromJson(json);
        Response response = httpResponseFacade.response();
        SoftAssertions softly = new SoftAssertions();
        Map<String, String> stringStringMap = jsonParser.parsePathToValueMap(json);
        for (Map.Entry<String, String> entry : stringStringMap.entrySet()) {
            String path = entry.getKey();
            String expectedValue = entry.getValue();
            Object actualValue = response.body().path(path);
            softly
                .assertThat(actualValue)
                .as("Value of property by path '%s'", path)
                .isEqualTo(expectedValue);
        }
        if(!softly.wasSuccess()) {
            softly.fail("Response was: \n" + response.body().prettyPrint());
        }
        softly.assertAll();
    }

    @Then("^HTTP response properties should match:$")
    @CukesDocs("Check http response contains properties from table")
    public void checkHttpResponsePropertiesMatch(List<Map<String, String>> properties) {
        Response response = httpResponseFacade.response();
        SoftAssertions softly = new SoftAssertions();

        String path, expectedValue;
        for (Map<String, String> propertyMap : properties) {
            path = propertyMap.get("path");
            expectedValue = inflater.inflate(propertyMap.get("value"));
            Object actualValue = response.body().path(path);
            softly
                .assertThat(actualValue)
                .as("Value of property by path '%s'", path)
                .isEqualTo(expectedValue);
        }
        if(!softly.wasSuccess()) {
            softly.fail("Response was: \n" + response.body().prettyPrint());
        }
        softly.assertAll();
    }

    @Then("^HTTP response property \"([^\"]+)\" array size should( not|) be (\\d+)$")
    @CukesDocs("Check http response property size")
    public void checkHttpResponsePropertySize(String path, String condition, Integer size) {
        boolean shouldBeEqual = condition.isEmpty();
        if(shouldBeEqual) {
            httpAssertionFacade.bodyContainsArrayWithSize(path, size.toString());
        } else {
            httpAssertionFacade.bodyContainsArrayWithSize(path, "<>"+size);
        }
    }

    @Then("^HTTP response property \"([^\"]+)\" array size should be (>=|>|<|<=) than (\\d+)$")
    @CukesDocs("Check http response property size is different than certain number")
    public void checkHttpResponsePropertySizeIsOtherThan(String path, String operator, Integer size) {
        httpAssertionFacade.bodyContainsArrayWithSize(path, operator + size);
    }

    @Then("^HTTP response property \"([^\"]+)\" length should( not|) be (\\d+)$")
    @CukesDocs("Check http response property length")
    public void checkHttpResponsePropertySize(String path, String condition, int expectedLength) {
        Response response = httpResponseFacade.response();
        String property = response.body().path(path).toString();
        int actualLength = property.length();
        boolean shouldBeEqual = condition.isEmpty();
        boolean isEqual = actualLength == expectedLength;
        Assert.assertTrue(
            "HTTP response property '" + path + "' should" + condition + " be " + expectedLength + " but was " + actualLength +
                "\nAnd property value is " + property +
                "\nAnd response body is:\n" + httpResponseFacade.response().body().prettyPrint() +
                "\nAnd request body is:\n" + world.get("requestBody").orNull(),
            isEqual == shouldBeEqual);
    }

    @Then("^HTTP response property \"([^\"]+)\" length should be (>=|>|<|<=) than (\\d+)$")
    @CukesDocs("Check http response property length is different than certain number")
    public void checkHttpResponsePropertySizeIsOtherThan(String path, String operation, int expectedLength) {
        Response response = httpResponseFacade.response();
        String property = response.body().path(path).toString();
        int actualLength = property.length();
        boolean result = false;
        switch (operation) {
            // @formatter:off
            case ">=":  result = actualLength >= expectedLength;    break;
            case ">":   result = actualLength > expectedLength;     break;
            case "<=":  result = actualLength <= expectedLength;    break;
            case "<":   result = actualLength < expectedLength;     break;
            // @formatter:on
        }
        Assert.assertTrue(
            "HTTP response property '" + path + "' should be " + operation + " than " + expectedLength + " but was " + actualLength +
                "\nAnd property value is " + property +
                "\nAnd response body is:\n" + httpResponseFacade.response().body().prettyPrint() +
                "\nAnd request body is:\n" + world.get("requestBody").orNull(),
            result);
    }
}
