package lv.ctco.cukes.http.matchers;

import io.restassured.response.Response;
import lv.ctco.cukes.core.internal.matchers.JsonMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath;

public class ResponseMatcher {

    // TODO
    public static Matcher<Response> aProperty(final String path, final Matcher<?> matcher) {
        return new TypeSafeMatcher<Response>() {

            @Override
            protected boolean matchesSafely(Response response) {
                //containsValueByPath
                return containsValueByPath(new JsonMatchers.ContentProvider<Response>() {
                    @Override
                    public String getValue(Object o) {
                        return ((Response) o).getBody().asString();
                    }

                    @Override
                    public String getContentType(Object o) {
                        return ((Response) o).getContentType();
                    }
                }, path, matcher).matches(response);
            }

            @Override
            public void describeTo(Description description) {
//                description.appendText("has statusCode").appendDescriptionOf(statusCodeMatches);
            }

            @Override
            protected void describeMismatchSafely(Response item, Description mismatchDescription) {
//                mismatchDescription.appendText("statusCode<").appendValue(item.statusCode()+"").appendText(">");
            }
        };
    }

    public static Matcher<Response> aHeader(final String header, final Matcher<?> matcher) {
        return new TypeSafeMatcher<Response>() {

            @Override
            protected boolean matchesSafely(Response response) {
                String actualHeaderValue = response.getHeader(header);
                return matcher.matches(actualHeaderValue);
            }

            @Override
            public void describeTo(Description description) {
//                description.appendText("has statusCode").appendDescriptionOf(statusCodeMatches);
            }

            @Override
            protected void describeMismatchSafely(Response item, Description mismatchDescription) {
//                mismatchDescription.appendText("statusCode<").appendValue(item.statusCode()+"").appendText(">");
            }
        };
    }

    public static Matcher<Response> aStatusCode(final Matcher<Integer> statusCodeMatches) {
        return new TypeSafeMatcher<Response>() {

            @Override
            protected boolean matchesSafely(Response response) {
                return statusCodeMatches.matches(response.statusCode());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has statusCode").appendDescriptionOf(statusCodeMatches);
            }

            @Override
            protected void describeMismatchSafely(Response item, Description mismatchDescription) {
                mismatchDescription.appendText("statusCode<").appendValue(item.statusCode() + "").appendText(">");
            }
        };
    }
}
