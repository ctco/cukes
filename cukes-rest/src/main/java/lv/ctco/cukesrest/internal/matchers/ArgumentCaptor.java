package lv.ctco.cukesrest.internal.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ArgumentCaptor<T> extends BaseMatcher<T> {

    private T value;

    @Override
    public boolean matches(Object o) {
        value = (T) o;
        return true;
    }

    @Override
    public void describeTo(Description description) {

    }

    public T getValue() {
        return value;
    }
}
