package lv.ctco.cukesrest.internal.matchers;

import com.jayway.restassured.internal.RestAssuredResponseOptionsImpl;
import com.jayway.restassured.response.ResponseBodyExtractionOptions;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class JsonMatchers {

    // TODO: Collect and show all mismatch
    public static Matcher<ResponseBodyExtractionOptions> containsValueByPath(final String path, final Matcher<?> matcher) {
        return new BaseMatcher<ResponseBodyExtractionOptions>() {
            @Override
            public boolean matches(Object o) {
                try {
                    RestAssuredResponseOptionsImpl responseBody = (RestAssuredResponseOptionsImpl) o;
                    return matcher.matches(responseBody.path(path));
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Path " + path + " contains ");
                matcher.describeTo(description);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                RestAssuredResponseOptionsImpl responseBody = (RestAssuredResponseOptionsImpl) item;
                Object value = responseBody.path(path);
                matcher.describeMismatch(value, description);
            }
        };
    }
}
