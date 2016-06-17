package lv.ctco.cukesrest.internal.matchers;

import com.jayway.restassured.internal.*;
import com.jayway.restassured.path.xml.*;
import com.jayway.restassured.path.xml.config.*;
import com.jayway.restassured.response.*;
import lv.ctco.cukesrest.internal.helpers.*;
import org.hamcrest.*;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.*;

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
                    /* Fix for Unexisting .dtd https://github.com/rest-assured/rest-assured/issues/391 */
                    if (containsIgnoreCase(responseBody.getContentType(), "xml")) {
                        XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
                        value = responseBody.xmlPath(config).get(path);
                    } else {
                        value = responseBody.path(path);
                    }
                    /* Due to REST assured Compatibility Mode HTML */
                    if (Strings.containsIgnoreCase(responseBody.getContentType(), "html")) {
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
