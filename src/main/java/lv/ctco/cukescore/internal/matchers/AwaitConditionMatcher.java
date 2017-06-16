package lv.ctco.cukescore.internal.matchers;

import cucumber.runtime.CucumberException;
import lv.ctco.cukescore.internal.AwaitCondition;
import lv.ctco.cukescore.internal.switches.ResponseWrapper;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class AwaitConditionMatcher extends TypeSafeMatcher<ResponseWrapper> {

    private AwaitCondition awaitCondition;

    public AwaitConditionMatcher(AwaitCondition awaitCondition) {
        this.awaitCondition = awaitCondition;
    }

    @Override
    protected boolean matchesSafely(ResponseWrapper responseWrapper) {
        if (awaitCondition.getSuccessMatcher() != null && awaitCondition.getSuccessMatcher().matches(responseWrapper)) {
            return true;
        }
        if (awaitCondition.getFailureMatcher() != null && awaitCondition.getFailureMatcher().matches(responseWrapper)) {
            throw new CucumberException("Expected successful response but was failed.");
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Matches successful or failure response.");
    }
}
