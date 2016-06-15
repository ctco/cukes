package lv.ctco.cukesrest.internal.matchers;

import org.hamcrest.*;

import static org.hamcrest.core.Is.*;

public class MiscMatchers {

    public static <T> Matcher<T> that(Matcher<T> matcher) {
        return is(matcher);
    }
}
