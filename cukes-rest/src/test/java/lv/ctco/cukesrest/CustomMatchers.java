package lv.ctco.cukesrest;

import org.hamcrest.*;

import java.util.*;

public class CustomMatchers {
    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasSize(final int size) {
        return new TypeSafeMatcher<Map<? extends K, ? extends V>>() {
            @Override
            public boolean matchesSafely(Map<? extends K, ? extends V> kvMap) {
                return kvMap.size() == size;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" has ").appendValue(size).appendText(" key/value pairs");
            }
        };
    }

    public static Matcher<String> stringWithLength(final Matcher size) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String str) {
                return size.matches(str.length());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" has length ").appendValue(size);
            }
        };
    }
}
