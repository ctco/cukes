package lv.ctco.cukes.http.matchers;

import io.cucumber.core.exception.CucumberException;
import io.restassured.response.Response;
import lv.ctco.cukes.http.AwaitCondition;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class AwaitConditionMatcher extends TypeSafeMatcher<Response> {

    private final AwaitCondition awaitCondition;

    public AwaitConditionMatcher(AwaitCondition awaitCondition) {
        this.awaitCondition = awaitCondition;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Matches successful or failure response.");
    }

    @Override
    protected boolean matchesSafely(Response response) {
        if (awaitCondition.getSuccessMatcher() != null && awaitCondition.getSuccessMatcher().matches(response)) {
            return true;
        }
        if (awaitCondition.getFailureMatcher() != null && awaitCondition.getFailureMatcher().matches(response)) {
            throw new CucumberException("Expected successful response but was failed.");
        }
        return false;
    }
}
