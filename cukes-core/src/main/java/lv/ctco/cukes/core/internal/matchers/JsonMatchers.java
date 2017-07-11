package lv.ctco.cukes.core.internal.matchers;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class JsonMatchers {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMatchers.class);

    public static <T> Matcher<T> containsValueByPath(ContentProvider<T> contentProvider, final String path, final Matcher<?> matcher) {
        return new BaseMatcher<T>() {

            private Object value;

            @Override
            public boolean matches(Object o) {
                try {
                    this.value = retrieveValueByPath(contentProvider, o, path);
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

    public static <T> Matcher<T> containsValueByPathInArray(ContentProvider<T> contentProvider, final String path, final Matcher<?> matcher) {
        return new BaseMatcher<T>() {
            private Object value;

            @Override
            public boolean matches(Object o) {
                this.value = retrieveValueByPath(contentProvider, o, path);

                if (this.value instanceof List) {
                    List list = (List) this.value;
                    return matchObjectInArray(list.toArray());
                } else if (this.value instanceof Object[]) {
                    return matchObjectInArray((Object[]) this.value);
                } else {
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

    public static <T> Matcher<T> containsPropertyValueByPathInArray(ContentProvider<T> contentProvider, final String path, final String property, final Matcher<?> matcher) {
        return new BaseMatcher<T>() {
            private Object value;

            @Override
            public boolean matches(Object o) {
                this.value = retrieveValueByPath(contentProvider, o, path);

                if (this.value instanceof List) {
                    List list = (List) this.value;
                    return matchObjectPropertyInArray(property, list.toArray());
                } else if (this.value instanceof Object[]) {
                    return matchObjectPropertyInArray(property, (Object[]) this.value);
                } else {
                    return false;
                }
            }

            @SuppressWarnings("unchecked")
            private boolean matchObjectPropertyInArray(String property, Object[] objects) {
                for (Object object : objects) {
                    HashMap<String, String> map = (HashMap<String, String>) object;
                    if (map != null && matcher.matches(map.get(property))) {
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

    private static <T> Object retrieveValueByPath(ContentProvider<T> contentProvider, Object o, String path) {
        String contentType = contentProvider.getContentType(o);
        String body = contentProvider.getValue(o);
        Object value;
        if (containsIgnoreCase(contentType, "xml")) {
            XmlPathConfig config = new XmlPathConfig().disableLoadingOfExternalDtd();
            XmlPath xmlPath = new XmlPath(body);
            value = xmlPath.using(config).get(path);

        } else if (containsIgnoreCase(contentType, "html")) {
            XmlPath htmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, body);
            List<Object> list = htmlPath.getList(path);
            value =
                list.size() > 1
                    ? list
                    : htmlPath.getString(path);

        } else {
            JsonPathConfig config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
            JsonPath jsonPath = new JsonPath(body);
            value = jsonPath.using(config).get(path);
        }
        return value;
    }

    public interface ContentProvider<T> {
        String getValue(Object o);
        String getContentType(Object o);
    }

}
