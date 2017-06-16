package lv.ctco.cukescore.internal;

import lv.ctco.cukescore.internal.helpers.Time;
import lv.ctco.cukescore.internal.switches.ResponseWrapper;
import org.hamcrest.Matcher;

public class AwaitCondition {
    private final Time waitTime;
    private final Time interval;
    private final Matcher<ResponseWrapper> successMatcher;
    private final Matcher<ResponseWrapper> failureMatcher;

    public AwaitCondition(Time waitTime, Time interval, Matcher<ResponseWrapper> successMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.successMatcher = successMatcher;
        failureMatcher = null;
    }

    public AwaitCondition(Time waitTime, Time interval, Matcher<ResponseWrapper> successMatcher, Matcher<ResponseWrapper> failureMatcher) {
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

    public Matcher<ResponseWrapper> getSuccessMatcher() {
        return successMatcher;
    }

    public Matcher<ResponseWrapper> getFailureMatcher() {
        return failureMatcher;
    }
}
