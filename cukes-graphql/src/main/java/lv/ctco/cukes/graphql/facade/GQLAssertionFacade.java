package lv.ctco.cukes.graphql.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.matchers.*;
import lv.ctco.cukes.core.internal.switches.SwitchedBy;
import lv.ctco.cukes.http.matchers.StatusCodeMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static lv.ctco.cukes.core.internal.matchers.ArrayWithSizeMatcher.arrayWithSize;
import static lv.ctco.cukes.core.internal.matchers.EqualToIgnoringTypeMatcher.equalToIgnoringType;
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsPropertyValueByPathInArray;
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Singleton
@SwitchedBy(CukesOptions.ASSERTIONS_DISABLED)
@InflateContext
public class GQLAssertionFacade {

    @Inject
    private GlobalWorldFacade world;

    @Inject
    GQLResponseFacade responseFacade;

    private String getPath(String path) {
        return "data." + path;
    }

    public void responseContainsPropertyWithValue(String path, String value) {
        assertBodyValueByPath(path, equalToIgnoringType(value, this.world.getBoolean("case-insensitive")));
    }

    public void bodyContainsArrayWithSize(String path, String size) {
        assertBodyValueByPath(path, arrayWithSize(size));
    }

    public void bodyContainsArrayWithObjectHavingProperty(String path, String property, String value) {
        Response response = this.responseFacade.response();
        assertThat(response,
            containsPropertyValueByPathInArray(ResponseContentProvider.INSTANCE,
                getPath(path),
                property,
                equalToIgnoringType(value, this.world.getBoolean("case-insensitive")))
        );
    }

    private void assertBodyValueByPath(String path, Matcher matcher) {
        Response response = this.responseFacade.response();
        assertThat(response, containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), matcher));
    }

    public void bodyEqualTo(String body) {
        this.responseFacade.response().then().body(equalTo(body));
    }

    public void bodyNotEqualTo(String body) {
        this.responseFacade.response().then().body(not(equalTo(body)));
    }

    public void bodyNotEmpty() {
        this.responseFacade.response().then().body(not(isEmptyOrNullString()));
    }

    public void bodyContains(String body) {
        this.responseFacade.response().then().body(containsString(body));
    }

    public void bodyDoesNotContain(String body) {
        this.responseFacade.response().then().body(not(containsString(body)));
    }

    public void headerIsEmpty(String headerName) {
        this.responseFacade.response().then().header(headerName, isEmptyString());
    }

    public void headerIsNotEmpty(String headerName) {
        this.responseFacade.response().then().header(headerName, not(isEmptyString()));
    }

    public void statusCodeIs(final int statusCode) {
        final boolean appendBody = world.getBoolean(CukesOptions.ASSERTS_STATUS_CODE_DISPLAY_BODY, false);
        final Integer maxSize = world.get(CukesOptions.ASSERTS_STATUS_CODE_MAX_SIZE).transform(s -> Integer.parseInt(s)).orNull();
        final Response response = responseFacade.response();

        response.then().statusCode(new StatusCodeMatcher(statusCode, response, appendBody, maxSize));
    }

    public void statusCodeIsNot(int statusCode) {
        this.responseFacade.response().then().statusCode(not(statusCode));
    }

    public void headerEndsWith(String headerName, String suffix) {
        this.responseFacade.response().then().header(headerName, EndsWithRegexp.endsWithRegexp(suffix));
    }

    public void varAssignedFromHeader(@InflateContext.Ignore String varName, String headerName) {
        String value = this.responseFacade.response().getHeader(headerName);
        this.world.put(varName, value);
    }

    public void headerEqualTo(String headerName, String value) {
        this.responseFacade.response().then().header(headerName, equalTo(value));
    }

    public void headerNotEqualTo(String headerName, String value) {
        this.responseFacade.response().then().header(headerName, not(equalTo(value)));
    }

    public void headerContains(String headerName, String text) {
        this.responseFacade.response().then().header(headerName, containsString(text));
    }

    public void headerDoesNotContain(String headerName, String text) {
        this.responseFacade.response().then().header(headerName, not(containsString(text)));
    }

    public void bodyContainsPathWithOtherValue(String path, String value) {
        Response response = this.responseFacade.response();
        assertThat(response,
            JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), EqualToIgnoringTypeMatcher.notEqualToIgnoringType(value)));
    }

    public void bodyDoesNotContainPath(String path) {
        Object value = this.responseFacade.response().body().path(getPath(path));
        assertThat(value, nullValue());
    }

    public void bodyContainsPathOfType(String path, String type) {
        Response response = this.responseFacade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), OfTypeMatcher.ofType(type)));
    }

    public void bodyContainsPathMatchingPattern(String path, String pattern) {
        Response response = this.responseFacade.response();
        assertThat(response,
            JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), MiscMatchers.that(ContainsPattern.containsPattern(pattern))));
    }

    public void bodyContainsPathNotMatchingPattern(String path, String pattern) {
        Response response = this.responseFacade.response();
        assertThat(response,
            JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE,
                getPath(path),
                MiscMatchers.that(Matchers.not(ContainsPattern.containsPattern(pattern)))));
    }

    public void varAssignedFromProperty(@InflateContext.Ignore String varName, String property) {
        String value = String.valueOf(this.responseFacade.response().body().<Object>path(property));
        this.world.put(varName, value);
    }

    public void varAssignedFromBody(@InflateContext.Ignore String varName) {
        String value = this.responseFacade.response().body().asString();
        this.world.put(varName, value);
    }

    public void bodyContainsJsonPathValueContainingPhrase(String path, String phrase) {
        Response response = this.responseFacade.response();
        assertThat(response, JsonMatchers.containsValueByPath(ResponseContentProvider.INSTANCE, getPath(path), containsString(phrase)));
    }

    public void bodyContainsArrayWithEntryHavingValue(String path, String value) {
        Response response = this.responseFacade.response();
        assertThat(response, JsonMatchers.containsValueByPathInArray(ResponseContentProvider.INSTANCE, getPath(path),
            EqualToIgnoringTypeMatcher.equalToIgnoringType(value, this.world.getBoolean("case-insensitive"))));

    }
}
