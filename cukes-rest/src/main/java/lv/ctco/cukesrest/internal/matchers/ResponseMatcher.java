package lv.ctco.cukesrest.internal.matchers;

import lv.ctco.cukesrest.internal.switches.*;
import org.hamcrest.*;

import static lv.ctco.cukesrest.internal.matchers.JsonMatchers.*;

public class ResponseMatcher {

    // TODO
    public static Matcher<ResponseWrapper> aProperty(final String path, final Matcher<?> matcher) {
        return new TypeSafeMatcher<ResponseWrapper>() {

            @Override
            protected boolean matchesSafely(ResponseWrapper response) {
                //containsValueByPath
                return containsValueByPath(path, matcher).matches(response.getResponse());
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
