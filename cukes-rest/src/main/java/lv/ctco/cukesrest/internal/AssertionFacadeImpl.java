package lv.ctco.cukesrest.internal;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import lv.ctco.cukesrest.CukesOptions;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import lv.ctco.cukesrest.internal.context.InflateContext;
import lv.ctco.cukesrest.internal.json.JsonParser;
import lv.ctco.cukesrest.internal.matchers.*;
import lv.ctco.cukesrest.internal.switches.SwitchedBy;
import org.hamcrest.Matchers;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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

    public void statusCodeIs(final int statusCode) {

        final Response response = facade.response();
        final String body = response.getBody().print();

        /*
         * This is a temporary hack due to:
         * https://github.com/rest-assured/rest-assured/issues/781
         */
        try {
            response.then().statusCode(statusCode);
        } catch (AssertionError error) {
            if (world.getBoolean(CukesOptions.ASSERTS_STATUS_CODE_DISPLAY_BODY, false)) {
                final Optional<String> maxSizeOptional = world.get(CukesOptions.ASSERTS_STATUS_CODE_MAX_SIZE);
                final int size = body.length();

                String message = error.getMessage();
                message = message.replaceAll("\n$", "");

                if (response.getContentType().equals("application/octet-stream")) {
                    message += "\n\nBody:\n<binary>";
                } else if (maxSizeOptional.isPresent() && size > Integer.parseInt(maxSizeOptional.get())) {
                    message += "\n\nBody:\n<exceeds max size>";
                } else {
                    message += "\n\nBody:\n" + body;
                }

                throw new AssertionError(message);
            }

            throw error;
        }
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


    public void varAssignedFromProperty(@InflateContext.Ignore String varName, String property) {
        String value = String.valueOf(facade.response().body().path(property));
        world.put(varName, value);
    }

    public void varAssignedFromBody(@InflateContext.Ignore String varName) {
        String value = facade.response().body().asString();
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
