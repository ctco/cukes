package lv.ctco.cukesrest.internal.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OfTypeMatcher {

    public static Matcher<Object> ofType(final String type) {
        return new BaseMatcher<Object>() {
            @Override
            public boolean matches(Object obj) {
                try {
                    if (isOfType(obj, Integer.class)) {
                        return true;
                    } else if (isOfType(obj, Long.class)) {
                        return true;
                    } else if (isOfType(obj, String.class)) {
                        return true;
                    } else if (isOfType(obj, Float.class)) {
                        return true;
                    } else if (isOfType(obj, Double.class)) {
                        return true;
                    } else if (isOfType(obj, BigDecimal.class)) {
                        return true;
                    } else if (isOfType(obj, Boolean.class)) {
                        return true;
                    } else if (isOfType(obj, List.class)) {
                        return true;
                    } else if (isOfType(obj, Map.class)) {
                        return true;
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
