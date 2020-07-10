package lv.ctco.cukes.rest;

import com.yammer.metrics.core.HealthCheck;

public class SampleHealthCheck extends HealthCheck {

    protected SampleHealthCheck() {
        super("sample");
    }

    @Override
    protected Result check() {
        return Result.healthy(); // To Disable Dropwizard warnings
    }
}
