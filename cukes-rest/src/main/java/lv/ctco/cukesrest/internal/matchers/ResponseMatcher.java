package lv.ctco.cukesrest.internal.matchers;

import lv.ctco.cukesrest.internal.switches.*;
import org.hamcrest.*;

import static org.hamcrest.Matchers.*;

public class ResponseMatcher {

    public static Matcher<ResponseWrapper> statusCode(final int statusCode) {
        return statusCode(equalTo(statusCode));
    }

    public static Matcher<ResponseWrapper> statusCode(final Matcher<Integer> statusCodeMatches) {
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
