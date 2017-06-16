package lv.ctco.cukesrest;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class CustomMatchers {

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
