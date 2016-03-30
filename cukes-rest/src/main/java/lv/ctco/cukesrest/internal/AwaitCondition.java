package lv.ctco.cukesrest.internal;

import lv.ctco.cukesrest.internal.helpers.time.*;
import lv.ctco.cukesrest.internal.switches.*;
import org.hamcrest.*;

public class AwaitCondition {
    private final Time waitTime;
    private final Time interval;
    private final Matcher<ResponseWrapper> responseMatcher;

    public AwaitCondition(Time waitTime, Time interval, Matcher<ResponseWrapper> responseMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.responseMatcher = responseMatcher;
    }

    public Time getWaitTime() {
        return waitTime;
    }

    public Time getInterval() {
        return interval;
    }

    public Matcher<ResponseWrapper> getResponseMatcher() {
        return responseMatcher;
    }
}
