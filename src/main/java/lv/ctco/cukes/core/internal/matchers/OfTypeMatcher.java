package lv.ctco.cukes.core.internal.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OfTypeMatcher {

    public enum SupportedClass {
        INTEGER(Integer.class),
        LONG(Long.class),
        STRING(String.class),
        FLOAT(Float.class),
        DOUBLE(Double.class),
        BIGDECIMAL(BigDecimal.class),
        BOOLEAN(Boolean.class),
        LIST(List.class),
        MAP(Map.class),
        DATE(Date.class);

        private final Class className;

        SupportedClass(Class className) {
            this.className = className;
        }

        public Class getClassName() {
            return className;
        }
    }

    public static Matcher<Object> ofType(final String type) {
        return new BaseMatcher<Object>() {
            @Override
            public boolean matches(Object obj) {

                try {
                    for (SupportedClass supportedClass : SupportedClass.values()) {
                        if (isOfType(obj, supportedClass.getClassName())) {
                            return true;
                        }
                    }
                    return false;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("of type " + type);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                if (item != null) {
                    description.appendText("of type " + item.getClass().getSimpleName().toLowerCase());
                } else {
                    description.appendText("was null");
                }
            }

            private boolean isOfType(Object o, Class cls) {
                return cls.isInstance(o) && type.equalsIgnoreCase(cls.getSimpleName());
            }
        };
    }
}
