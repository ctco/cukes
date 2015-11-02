package lv.ctco.cukesrest.internal.matchers;

import com.jayway.restassured.internal.path.xml.*;
import com.jayway.restassured.path.xml.element.*;
import org.hamcrest.*;

import java.util.*;

import static java.lang.Integer.*;
import static org.hamcrest.Matchers.*;

public class ArrayWithSizeMatcher {
    public static Matcher<List<?>> arrayWithSize(final String size) {

        return new BaseMatcher<List<?>>() {

            @Override
            public boolean matches(Object item) {
                List<?> list = null;

                if (item instanceof List) { // If JSON
                    list = (List<?>) item;
                } else if (item instanceof NodeChildrenImpl) { // If XML
                    List<Node> nodes = ((NodeChildrenImpl) item).list();
                    List<String> result = new ArrayList<String>(nodes.size());
                    for (Node node : nodes) {
                        result.add(node.value());
                    }
                    list = result;
                }

                Matcher<Integer> matcher;
                if (size.startsWith(">=")) {
                    matcher = greaterThanOrEqualTo(valueOf(size.substring(2)));
                } else if (size.startsWith(">")) {
                    matcher = greaterThan(valueOf(size.substring(1)));
                } else if (size.startsWith("<=")) {
                    matcher = lessThanOrEqualTo(valueOf(size.substring(2)));
                } else if (size.startsWith("<")) {
                    matcher = lessThan(valueOf(size.substring(1)));
                } else {
                    matcher = is(valueOf(size));
                }

                return hasSize(matcher).matches(list);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("array with size " + size);
            }
        };
    }
}
