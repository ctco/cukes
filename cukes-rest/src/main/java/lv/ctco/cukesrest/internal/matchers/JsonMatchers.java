package lv.ctco.cukesrest.internal.matchers;

import com.jayway.restassured.internal.RestAssuredResponseOptionsImpl;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.path.xml.config.XmlPathConfig;
import com.jayway.restassured.response.ResponseBodyExtractionOptions;
import lv.ctco.cukesrest.internal.helpers.Strings;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class JsonMatchers {


    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMatchers.class);

    // TODO: Collect and show all mismatch
    public static Matcher<ResponseBodyExtractionOptions> containsValueByPath(final String path, final Matcher<?> matcher) {
        return new BaseMatcher<ResponseBodyExtractionOptions>() {

            private Object value;

            @Override
            public boolean matches(Object o) {
                try {
                    RestAssuredResponseOptionsImpl responseBody = (RestAssuredResponseOptionsImpl) o;
                    /*
                     * Fix for Unexisting .dtd
                     * https://github.com/rest-assured/rest-assured/issues/391
                     */
                    if (containsIgnoreCase(responseBody.getContentType(), "xml")) {
                        XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
                        this.value = responseBody.xmlPath(config).get(path);
                    } else {
                        this.value = responseBody.path(path);
                    }
                    /* Due to REST assured Compatibility Mode HTML */
                    if (Strings.containsIgnoreCase(responseBody.getContentType(), "html")) {
                        List<Object> list = ((XmlPath) this.value).getList(path);
                        this.value = list.size() > 1 ? list : ((XmlPath) this.value).getString(path);
                    }
                    return matcher.matches(this.value);
                } catch (Exception e) {
                    LOGGER.info(e.getMessage(), e);
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

    // TODO: Collect and show all mismatch
    public static Matcher<ResponseBodyExtractionOptions> containsValueByPathInArray(final String path, final Matcher<?> matcher) {
        return new BaseMatcher<ResponseBodyExtractionOptions>() {

            private Object value;

            @Override
            public boolean matches(Object o) {
                try {
                    RestAssuredResponseOptionsImpl responseBody = (RestAssuredResponseOptionsImpl) o;
                    /*
                     * Fix for Unexisting .dtd
                     * https://github.com/rest-assured/rest-assured/issues/391
                     */
                    if (containsIgnoreCase(responseBody.getContentType(), "xml")) {
                        XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
                        this.value = responseBody.xmlPath(config).get(path);
                    } else {
                        this.value = responseBody.path(path);
                    }
                    /* Due to REST assured Compatibility Mode HTML */
                    if (Strings.containsIgnoreCase(responseBody.getContentType(), "html")) {
                        List<Object> list = ((XmlPath) this.value).getList(path);
                        this.value = list.size() > 1 ? list : ((XmlPath) this.value).getString(path);
                    }
                    if (this.value instanceof List) {
                        List<Object> list = (List) this.value;
                        return matchObjectInArray(list.toArray());
                    } else if (this.value instanceof Object[]) {
                        return matchObjectInArray((Object[]) this.value);
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }

            private boolean matchObjectInArray(Object[] objects) {
                for (Object object : objects) {
                    if (matcher.matches(object)) {
                        return true;
                    }
                }
                return false;
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
