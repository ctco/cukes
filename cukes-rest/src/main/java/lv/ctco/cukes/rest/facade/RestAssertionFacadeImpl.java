package lv.ctco.cukes.rest.facade;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.json.JsonParser;
import lv.ctco.cukes.core.internal.matchers.ArrayWithSizeMatcher;
import lv.ctco.cukes.core.internal.matchers.ContainsPattern;
import lv.ctco.cukes.core.internal.matchers.EndsWithRegexp;
import lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher;
import lv.ctco.cukes.core.internal.matchers.JsonMatchers;
import lv.ctco.cukes.core.internal.matchers.MiscMatchers;
import lv.ctco.cukes.core.internal.matchers.OfTypeMatcher;
import lv.ctco.cukes.core.internal.matchers.StatusCodeMatcher;
import lv.ctco.cukes.core.internal.switches.SwitchedBy;
import org.hamcrest.Matchers;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@Singleton
@SwitchedBy(CukesOptions.ASSERTIONS_DISABLED)
@InflateContext
public class RestAssertionFacadeImpl implements RestAssertionFacade {

    @Inject
    private GlobalWorldFacade world;

    @Inject
    private JsonParser jsonParser;

    @Inject
    RestResponseFacade facade;

    @Override
    public void bodyEqualTo(String body) {
        this.facade.response().then().body(equalTo(body));
    }

    @Override
    public void bodyNotEqualTo(String body) {
        this.facade.response().then().body(not(equalTo(body)));
    }

    @Override
    public void bodyNotEmpty() {
        this.facade.response().then().body(not(isEmptyOrNullString()));
    }

    @Override
    public void bodyContains(String body) {
        this.facade.response().then().body(containsString(body));
    }

    @Override
    public void bodyDoesNotContain(String body) {
        this.facade.response().then().body(not(containsString(body)));
    }

    @Override
    public void headerIsEmpty(String headerName) {
        this.facade.response().then().header(headerName, isEmptyString());
    }

    @Override
    public void headerIsNotEmpty(String headerName) {
        this.facade.response().then().header(headerName, not(isEmptyString()));
    }

    @Override
    public void statusCodeIs(final int statusCode) {
        final boolean appendBody = world.getBoolean(CukesOptions.ASSERTS_STATUS_CODE_DISPLAY_BODY, false);
        final Integer maxSize = world.get(CukesOptions.ASSERTS_STATUS_CODE_MAX_SIZE).transform(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        }).orNull();
        final Response response = facade.response();

        response.then().statusCode(new StatusCodeMatcher(statusCode, response, appendBody, maxSize));
    }

    @Override
    public void statusCodeIsNot(int statusCode) {
        this.facade.response().then().statusCode(not(statusCode));
    }

    @Override
    public void headerEndsWith(String headerName, String suffix) {
        this.facade.response().then().header(headerName, EndsWithRegexp.endsWithRegexp(suffix));
    }

    @Override
    public void varAssignedFromHeader(@InflateContext.Ignore String varName, String headerName) {
        String value = this.facade.response().getHeader(headerName);
        this.world.put(varName, value);
    }

    @Override
    public void headerEqualTo(String headerName, String value) {
        this.facade.response().then().header(headerName, equalTo(value));
    }

    @Override
    public void headerNotEqualTo(String headerName, String value) {
        this.facade.response().then().header(headerName, not(equalTo(value)));
    }

    @Override
    public void headerContains(String headerName, String text) {
        this.facade.response().then().header(headerName, containsString(text));
    }

    @Override
    public void headerDoesNotContain(String headerName, String text) {
        this.facade.response().then().header(headerName, not(containsString(text)));
    }

    @Override
    public void bodyContainsPropertiesFromJson(String json) {
        // TODO: make multiple failures visible. Rework to allOf() matcher
        // TODO: Implement XML ?
        Map<String, String> stringStringMap = this.jsonParser.parsePathToValueMap(json);
        for (Map.Entry<String, String> entry : stringStringMap.entrySet()) {
            bodyContainsPathWithValue(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void bodyContainsPathWithValue(String path, String value) {
        Response response = this.facade.response();
        assertThat(response,
                JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, path, EqualToIgnoringTypeMatcher.equalToIgnoringType(value, this.world.getBoolean("case-insensitive"))));
    }

    @Override
    public void bodyContainsPathWithOtherValue(String path, String value) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, path, EqualToIgnoringTypeMatcher.notEqualToIgnoringType(value)));
    }

    @Override
    public void bodyDoesNotContainPath(String path) {
        Object value = this.facade.response().body().path(path);
        assertThat(value, nullValue());
    }

    @Override
    public void bodyContainsArrayWithSize(String path, String size) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, path, ArrayWithSizeMatcher.arrayWithSize(size)));
    }

    @Override
    public void bodyContainsPathOfType(String path, String type) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, path, OfTypeMatcher.ofType(type)));
    }

    @Override
    public void bodyContainsPathMatchingPattern(String path, String pattern) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, path, MiscMatchers.that(ContainsPattern.containsPattern(pattern))));
    }

    @Override
    public void bodyContainsPathNotMatchingPattern(String path, String pattern) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, path, MiscMatchers.that(Matchers.not(ContainsPattern.containsPattern(pattern)))));
    }

    @Override
    public void varAssignedFromProperty(@InflateContext.Ignore String varName, String property) {
        String value = String.valueOf(this.facade.response().body().<Object>path(property));
        this.world.put(varName, value);
    }

    @Override
    public void varAssignedFromBody(@InflateContext.Ignore String varName) {
        String value = this.facade.response().body().asString();
        this.world.put(varName, value);
    }

    @Override
    public void bodyContainsJsonPathValueContainingPhrase(String path, String phrase) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, path, containsString(phrase)));
    }

    @Override
    public void failureOccurs(String exceptionClass) {
        assertThat(this.facade.getException().getClass().getSimpleName(), is(exceptionClass));
    }

    @Override
    public void failureIsExpected() {
        this.facade.setExpectException(true);
    }

    @Override
    public void bodyContainsArrayWithEntryHavingValue(String path, String value) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPathInArray(ResponseContentProvider.INSTANCE, path,
                EqualToIgnoringTypeMatcher.equalToIgnoringType(value, this.world.getBoolean("case-insensitive"))));

    }
}
