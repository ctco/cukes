package lv.ctco.cukescore.internal.matchers;

import io.restassured.internal.RestAssuredResponseOptionsImpl;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import io.restassured.response.ResponseBodyExtractionOptions;
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
                    String contentType = responseBody.getContentType();

                    if (containsIgnoreCase(contentType, "xml")) {
                        XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
                        this.value = responseBody.xmlPath(config).get(path);

                    } else if (containsIgnoreCase(contentType, "html")) {
                        XmlPath htmlPath = responseBody.htmlPath();
                        List<Object> list = htmlPath.getList(path);
                        this.value =
                            list.size() > 1
                                ? list
                                : htmlPath.getString(path);

                    } else {
                        JsonPathConfig config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
                        this.value = responseBody.jsonPath(config).get(path);
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
                    if (containsIgnoreCase(responseBody.getContentType(), "xml")) {
                        XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
                        this.value = responseBody.xmlPath(config).get(path);
                    } else if (containsIgnoreCase(responseBody.getContentType(), "html")) {
                        XmlPath htmlPath = responseBody.htmlPath();
                        List<Object> list = htmlPath.getList(path);
                        this.value =
                            list.size() > 1
                                ? list
                                : htmlPath.getString(path);
                    } else {
                        JsonPathConfig config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
                        this.value = responseBody.jsonPath(config).get(path);
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
