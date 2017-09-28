package lv.ctco.cukes.http;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

import java.util.Map;

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

    public static <T> Matcher<Optional<T>> equalToOptional(final T operand) {
        return new TypeSafeMatcher<Optional<T>>() {

            private Matcher<T> equalTo;

            @Override
            protected boolean matchesSafely(Optional<T> t) {
                if(t.isPresent()) {
                    equalTo = IsEqual.equalTo(t.get());
                    return equalTo.matches(operand);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                IsEqual.equalTo(equalTo).describeTo(description);
            }
        };
    }

}
