package lv.ctco.cukesrest.internal.matchers;

import com.jayway.restassured.internal.RestAssuredResponseOptionsImpl;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.ResponseBodyExtractionOptions;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class JsonMatchers {

    // TODO: Collect and show all mismatch
    public static Matcher<ResponseBodyExtractionOptions> containsValueByPath(final String path,
                                                                             final Matcher<?> matcher) {
        return new BaseMatcher<ResponseBodyExtractionOptions>() {

            private Object value;

            @Override
            public boolean matches(Object o) {
                try {
                    RestAssuredResponseOptionsImpl responseBody = (RestAssuredResponseOptionsImpl) o;
                    value = responseBody.path(path);
                    /* Due to REST assured Compatibility Mode HTML */
                    if (containsIgnoreCase(responseBody.getContentType(), "html")) {
                        List<Object> list = ((XmlPath) value).getList(path);
                        value = list.size() > 1 ? list : ((XmlPath) value).getString(path);
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
                matcher.describeMismatch(this.value, description);
            }
        };
    }
}
