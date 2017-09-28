package lv.ctco.cukes.http.facade;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.matchers.*;
import lv.ctco.cukes.core.internal.switches.SwitchedBy;
import lv.ctco.cukes.http.json.JsonParser;
import lv.ctco.cukes.http.matchers.StatusCodeMatcher;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;

import java.util.Map;

import static lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher.equalToIgnoringType;
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsPropertyValueByPathInArray;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Singleton
@SwitchedBy(CukesOptions.ASSERTIONS_DISABLED)
@InflateContext
public class HttpAssertionFacadeImpl implements HttpAssertionFacade {

    @Inject
    private GlobalWorldFacade world;

    @Inject
    private JsonParser jsonParser;

    @Inject
    HttpResponseFacade facade;

    private String getPath(String path) {
        if (!StringUtils.isEmpty(facade.getResponsePrefix())) {
            return facade.getResponsePrefix() + path;
        } else {
            return path;
        }
    }

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
            JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), EqualToIgnoringTypeMatcher.equalToIgnoringType(value, this.world.getBoolean("case-insensitive"))));
    }

    @Override
    public void bodyContainsPathWithOtherValue(String path, String value) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), EqualToIgnoringTypeMatcher.notEqualToIgnoringType(value)));
    }

    @Override
    public void bodyDoesNotContainPath(String path) {
        Object value = this.facade.response().body().path(getPath(path));
        assertThat(value, nullValue());
    }

    @Override
    public void bodyContainsArrayWithSize(String path, String size) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), ArrayWithSizeMatcher.arrayWithSize(size)));
    }

    @Override
    public void bodyContainsPathOfType(String path, String type) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), OfTypeMatcher.ofType(type)));
    }

    @Override
    public void bodyContainsPathMatchingPattern(String path, String pattern) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), MiscMatchers.that(ContainsPattern.containsPattern(pattern))));
    }

    @Override
    public void bodyContainsPathNotMatchingPattern(String path, String pattern) {
        Response response = this.facade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), MiscMatchers.that(Matchers.not(ContainsPattern.containsPattern(pattern)))));
    }

    @Override
    public void varAssignedFromProperty(@InflateContext.Ignore String varName, String property) {
        String value = String.valueOf(this.facade.response().body().<Object>path(getPath(property)));
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
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), containsString(phrase)));
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
        assertThat(response, JsonMatchers.containsValueByPathInArray(ResponseContentProvider.INSTANCE, getPath(path),
            EqualToIgnoringTypeMatcher.equalToIgnoringType(value, this.world.getBoolean("case-insensitive"))));
    }

    @Override
    public void bodyContainsArrayWithObjectHavingProperty(String path, String property, String value) {
        Response response = this.facade.response();
        assertThat(response,
            containsPropertyValueByPathInArray(ResponseContentProvider.INSTANCE,
                getPath(path),
                property,
                equalToIgnoringType(value, this.world.getBoolean("case-insensitive")))
        );
    }

}
