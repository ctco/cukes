package lv.ctco.cukes.rest.loadrunner;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CustomMatchers {

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
