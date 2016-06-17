package lv.ctco.cukesrest.internal;

import com.google.inject.*;
import com.jayway.restassured.response.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.internal.json.*;
import lv.ctco.cukesrest.internal.matchers.*;
import lv.ctco.cukesrest.internal.switches.*;
import org.hamcrest.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@Singleton
@SwitchedBy(CukesOptions.ASSERTIONS_DISABLED)
@InflateContext
public class AssertionFacadeImpl implements AssertionFacade {

    @Inject
    private GlobalWorldFacade world;

    @Inject
    private JsonParser jsonParser;

    @Inject
    ResponseFacade facade;

    public void bodyEqualTo(String body) {
        facade.response().then().body(equalTo(body));
    }

    public void bodyNotEqualTo(String body) {
        facade.response().then().body(not(equalTo(body)));
    }

    public void bodyNotEmpty() {
        facade.response().then().body(not(isEmptyOrNullString()));
    }

    public void bodyContains(String body) {
        facade.response().then().body(containsString(body));
    }

    public void bodyDoesNotContain(String body) {
        facade.response().then().body(not(containsString(body)));
    }

    public void headerIsEmpty(String headerName) {
        facade.response().then().header(headerName, isEmptyString());
    }

    public void headerIsNotEmpty(String headerName) {
        facade.response().then().header(headerName, not(isEmptyString()));
    }

    public void statusCodeIs(int statusCode) {
        facade.response().then().statusCode(statusCode);
    }

    public void statusCodeIsNot(int statusCode) {
        facade.response().then().statusCode(not(statusCode));
    }

    public void headerEndsWith(String headerName, String suffix) {
        facade.response().then().header(headerName, EndsWithRegexp.endsWithRegexp(suffix));
    }

    public void varAssignedFromHeader(@InflateContext.Ignore String varName, String headerName) {
        String value = facade.response().getHeader(headerName);
        world.put(varName, value);
    }

    public void headerEqualTo(String headerName, String value) {
        facade.response().then().header(headerName, equalTo(value));
    }

    public void headerNotEqualTo(String headerName, String value) {
        facade.response().then().header(headerName, not(equalTo(value)));
    }

    public void headerContains(String headerName, String text) {
        facade.response().then().header(headerName, containsString(text));
    }

    public void headerDoesNotContain(String headerName, String text) {
        facade.response().then().header(headerName, not(containsString(text)));
    }

    public void bodyContainsPropertiesFromJson(String json) {
        // TODO: make multiple failures visible. Rework to allOf() matcher
        // TODO: Implement XML ?
        Map<String, String> stringStringMap = jsonParser.parsePathToValueMap(json);
        for (Map.Entry<String, String> entry : stringStringMap.entrySet()) {
            bodyContainsPathWithValue(entry.getKey(), entry.getValue());
        }
    }

    public void bodyContainsPathWithValue(String path, String value) {
        ResponseBody responseBody = facade.response().body();
        assertThat(responseBody, JsonMatchers.containsValueByPath(path, EqualToIgnoringTypeMatcher
            .equalToIgnoringType(value)));
    }

    public void bodyContainsPathWithOtherValue(String path, String value) {
        ResponseBody responseBody = facade.response().body();
        assertThat(responseBody, JsonMatchers.containsValueByPath(path, EqualToIgnoringTypeMatcher
            .notEqualToIgnoringType(value)));
    }

    public void bodyDoesNotContainPath(String path) {
        Object value = facade.response().body().path(path);
        assertThat(value, nullValue());
    }

    public void bodyContainsArrayWithSize(String path, String size) {
        ResponseBody responseBody = facade.response().body();
        assertThat(responseBody, JsonMatchers.containsValueByPath(path, ArrayWithSizeMatcher.arrayWithSize(size)));
    }

    public void bodyContainsPathOfType(String path, String type) {
        ResponseBody responseBody = facade.response().body();
        assertThat(responseBody, JsonMatchers.containsValueByPath(path, OfTypeMatcher.ofType(type)));
    }

    public void bodyContainsPathMatchingPattern(String path, String pattern) {
        ResponseBody responseBody = facade.response().body();
        assertThat(responseBody, JsonMatchers.containsValueByPath(path, MiscMatchers.that(ContainsPattern
            .containsPattern(pattern))));
    }

    public void bodyContainsPathNotMatchingPattern(String path, String pattern) {
        ResponseBody responseBody = facade.response().body();
        assertThat(responseBody, JsonMatchers.containsValueByPath(path, MiscMatchers.that(Matchers.not
            (ContainsPattern.containsPattern(pattern)))));
    }

    // TODO: Experimental
    public void varAssignedFromProperty(@InflateContext.Ignore String varName, String property) {
        String value = String.valueOf(facade.response().body().path(property));
        world.put(varName, value);
    }

    public void bodyContainsJsonPathValueContainingPhrase(String path, String phrase) {
        ResponseBody responseBody = facade.response().body();
        assertThat(responseBody, JsonMatchers.containsValueByPath(path, containsString(phrase)));
    }

    @Override
    public void failureOccurs(String exceptionClass) {
        assertThat(facade.getException().getClass().getSimpleName(), is(exceptionClass));
    }

    @Override
    public void failureIsExpected() {
        facade.setExpectException(true);
    }
}
