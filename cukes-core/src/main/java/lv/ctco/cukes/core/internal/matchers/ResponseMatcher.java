package lv.ctco.cukes.core.internal.matchers;

import io.restassured.response.Response;
import lv.ctco.cukes.core.internal.switches.ResponseWrapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath;

public class ResponseMatcher {

    // TODO
    public static Matcher<ResponseWrapper> aProperty(final String path, final Matcher<?> matcher) {
        return new TypeSafeMatcher<ResponseWrapper>() {

            @Override
            protected boolean matchesSafely(ResponseWrapper response) {
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
                }, path, matcher).matches(response.getResponse());
            }

            @Override
            public void describeTo(Description description) {
//                description.appendText("has statusCode").appendDescriptionOf(statusCodeMatches);
            }

            @Override
            protected void describeMismatchSafely(ResponseWrapper item, Description mismatchDescription) {
//                mismatchDescription.appendText("statusCode<").appendValue(item.statusCode()+"").appendText(">");
            }
        };
    }

    public static Matcher<ResponseWrapper> aHeader(final String header, final Matcher<?> matcher) {
        return new TypeSafeMatcher<ResponseWrapper>() {

            @Override
            protected boolean matchesSafely(ResponseWrapper response) {
                String actualHeaderValue = response.getResponse().header(header);
                return matcher.matches(actualHeaderValue);
            }

            @Override
            public void describeTo(Description description) {
//                description.appendText("has statusCode").appendDescriptionOf(statusCodeMatches);
            }

            @Override
            protected void describeMismatchSafely(ResponseWrapper item, Description mismatchDescription) {
//                mismatchDescription.appendText("statusCode<").appendValue(item.statusCode()+"").appendText(">");
            }
        };
    }

    public static Matcher<ResponseWrapper> aStatusCode(final Matcher<Integer> statusCodeMatches) {
        return new TypeSafeMatcher<ResponseWrapper>() {

            @Override
            protected boolean matchesSafely(ResponseWrapper response) {
                return statusCodeMatches.matches(response.statusCode());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has statusCode").appendDescriptionOf(statusCodeMatches);
            }

            @Override
            protected void describeMismatchSafely(ResponseWrapper item, Description mismatchDescription) {
                mismatchDescription.appendText("statusCode<").appendValue(item.statusCode() + "").appendText(">");
            }
        };
    }
}
