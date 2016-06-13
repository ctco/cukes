package lv.ctco.cukesrest.internal;

import lv.ctco.cukesrest.internal.helpers.time.*;
import lv.ctco.cukesrest.internal.switches.*;
import org.hamcrest.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AwaitCondition {
    private final Time waitTime;
    private final Time interval;
    private final Matcher<ResponseWrapper> responseMatcher;
    private final Matcher<ResponseWrapper> failureMatcher;

    public AwaitCondition(Time waitTime, Time interval, Matcher<ResponseWrapper> responseMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.responseMatcher = responseMatcher;
        failureMatcher = null;
    }

    public AwaitCondition(Time waitTime, Time interval, Matcher<ResponseWrapper> responseMatcher, Matcher<ResponseWrapper> failureMatcher) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.responseMatcher = responseMatcher;
        this.failureMatcher = failureMatcher;
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

    public Matcher<ResponseWrapper> getFailureMatcher() {
        return failureMatcher;
    }
}
