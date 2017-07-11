package lv.ctco.cukes.rabbitmq.internal;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import lv.ctco.cukes.core.internal.helpers.Strings;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

//TODO forked from cukes-rest with some adoptations
public class CustomJsonMatchers {


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomJsonMatchers.class);

    // TODO: Collect and show all mismatch
    public static <T> Matcher<T> containsValueByPath(final ContentProvider<T> provider, final String path, final Matcher<?> matcher) {
        return new BaseMatcher<T>() {

            private Object value;

            @Override
            public boolean matches(Object o) {
                try {
                    String contentType = provider.getContentType(o);
                    String body = provider.getValue(o);
                    /*
                     * Fix for Unexisting .dtd
                     * https://github.com/rest-assured/rest-assured/issues/391
                     */
                    if (containsIgnoreCase(contentType, "xml")) {
                        XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
                        XmlPath xmlPath = new XmlPath(body).using(config);
                        this.value = xmlPath.get(path);
                    } else {
                        JsonPathConfig config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
                        JsonPath jsonPath = new JsonPath(body).using(config);
                        this.value = jsonPath.get(path);
                    }
                    /* Due to REST assured Compatibility Mode HTML */
                    if (Strings.containsIgnoreCase(contentType, "html")) {
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
    public static <T> Matcher<T> containsValueByPathInArray(final ContentProvider<T> provider, final String path, final Matcher<?> matcher) {
        return new BaseMatcher<T>() {

            private Object value;

            @Override
            public boolean matches(Object o) {
                try {
                    String contentType = provider.getContentType(o);
                    String body = provider.getValue(o);
                    /*
                     * Fix for Unexisting .dtd
                     * https://github.com/rest-assured/rest-assured/issues/391
                     */
                    if (containsIgnoreCase(contentType, "xml")) {
                        XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
                        XmlPath xmlPath = new XmlPath(body).using(config);
                        this.value = xmlPath.get(path);
                    } else {
                        JsonPath jsonPath = new JsonPath(body);
                        this.value = jsonPath.get(path);
                    }
                    /* Due to REST assured Compatibility Mode HTML */
                    if (Strings.containsIgnoreCase(contentType, "html")) {
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

    public interface ContentProvider<T> {
        String getValue(Object o);
        String getContentType(Object o);
    }

    public static class DefaultContentProvider<T> implements ContentProvider<T> {

        private String value;
        private String contentType;

        public DefaultContentProvider(String value, String contentType) {
            this.value = value;
            this.contentType = contentType;
        }

        @Override
        public String getValue(Object o) {
            return value;
        }

        @Override
        public String getContentType(Object o) {
            return contentType;
        }
    }
}
