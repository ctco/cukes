package lv.ctco.cukes.http.matchers;

import io.restassured.response.Response;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static java.lang.String.format;

public class StatusCodeMatcher extends TypeSafeDiagnosingMatcher<Integer> {

    private final Integer expectedStatusCode;
    private final Response response;
    private final boolean appendBody;
    private final Integer maxSize;

    public StatusCodeMatcher(Integer expectedStatusCode, Response response, boolean appendBody, Integer maxSize) {
        this.expectedStatusCode = expectedStatusCode;
        this.response = response;
        this.appendBody = appendBody;
        this.maxSize = maxSize;
    }

    @Override
    protected boolean matchesSafely(Integer statusCode, Description description) {
        description.appendText(format("was \"%d\"", statusCode));

        if (appendBody) {
            final String body = response.body().asString();
            final int size = body.length();

            if (response.getContentType().equals("application/octet-stream")) {
                description.appendText(" with body <binary>");
            } else if (maxSize != null && size > maxSize) {
                description.appendText(" with body <exceeding max size to show>");
            } else {
                description.appendText(format(" with body:\n\"\"\"\n%s\n\"\"\"", body));
            }
        }

        return expectedStatusCode.equals(statusCode);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(format("\"%d\"", expectedStatusCode));
    }
}
