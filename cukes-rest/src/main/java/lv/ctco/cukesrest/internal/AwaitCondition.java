package lv.ctco.cukesrest.internal;

import lv.ctco.cukesrest.internal.switches.ResponseWrapper;
import org.hamcrest.Matcher;

public class AwaitCondition {
    private final Time waitTime;
    private final Time interval;
    private final Matcher<ResponseWrapper> statusCode;

    public AwaitCondition(Time waitTime, Time interval, Matcher<ResponseWrapper> statusCode) {
        this.waitTime = waitTime;
        this.interval = interval;
        this.statusCode = statusCode;
    }

    public Time getWaitTime() {
        return waitTime;
    }

    public Time getInterval() {
        return interval;
    }

    public Matcher<ResponseWrapper> getStatusCode() {
        return statusCode;
    }
}
