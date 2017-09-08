package lv.ctco.cukes.http;

import io.restassured.response.Response;
import lv.ctco.cukes.core.internal.helpers.Time;
import org.hamcrest.Matcher;

public class AwaitCondition {

    private final Time waitTime;
    private final Time interval;
    private final Matcher<Response> successMatcher;
    private final Matcher<Response> failureMatcher;

    public AwaitCondition(Time waitTime, Time interval, Matcher<Response> successMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.successMatcher = successMatcher;
        failureMatcher = null;
    }

    public AwaitCondition(Time waitTime, Time interval, Matcher<Response> successMatcher, Matcher<Response> failureMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.successMatcher = successMatcher;
        this.failureMatcher = failureMatcher;
    }

    public Time getWaitTime() {
        return waitTime;
    }

    public Time getInterval() {
        return interval;
    }

    public Matcher<Response> getSuccessMatcher() {
        return successMatcher;
    }

    public Matcher<Response> getFailureMatcher() {
        return failureMatcher;
    }
}
