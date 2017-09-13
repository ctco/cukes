package lv.ctco.cukes.core.internal.matchers;

import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.path.xml.element.Node;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;
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
                } else if (size.startsWith("<>")) {
                    matcher = not(valueOf(size));
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
