package lv.ctco.cukes.core.internal.matchers;

import org.hamcrest.Matcher;

import static org.hamcrest.core.Is.is;

public class MiscMatchers {

    public static <T> Matcher<T> that(Matcher<T> matcher) {
        return is(matcher);
    }
}
