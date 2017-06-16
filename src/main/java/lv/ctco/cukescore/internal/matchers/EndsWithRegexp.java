package lv.ctco.cukescore.internal.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.regex.Pattern;

public class EndsWithRegexp {

    public static Matcher<String> endsWithRegexp(final String regexp) {
        return new BaseMatcher<String>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("matches pattern " + regexp);
            }

            @Override
            public boolean matches(Object item) {
                String value = (String) item;
                java.util.regex.Matcher matcher = Pattern.compile(".*" + regexp).matcher(value);

                return matcher.matches();
            }
        };
    }
}
