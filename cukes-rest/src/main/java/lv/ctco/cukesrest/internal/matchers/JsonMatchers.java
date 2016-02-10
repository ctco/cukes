package lv.ctco.cukesrest.internal.matchers;

import com.jayway.restassured.internal.*;
import com.jayway.restassured.path.xml.*;
import com.jayway.restassured.response.*;
import org.hamcrest.*;

import static org.apache.commons.lang3.StringUtils.*;

public class JsonMatchers {

    // TODO: Collect and show all mismatch
    public static Matcher<ResponseBodyExtractionOptions> containsValueByPath(final String path,
                                                                             final Matcher<?> matcher) {
        return new BaseMatcher<ResponseBodyExtractionOptions>() {
            @Override
            public boolean matches(Object o) {
                try {
                    RestAssuredResponseOptionsImpl responseBody = (RestAssuredResponseOptionsImpl) o;
                    Object value = responseBody.path(path);
                    /* Due to REST assured Compatibility Mode HTML */
                    if (containsIgnoreCase(responseBody.getContentType(), "html")) {
                        value = ((XmlPath) value).getString(path);
                    }
                    return matcher.matches(value);
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
