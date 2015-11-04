package lv.ctco.cukesrest;

import com.yammer.metrics.core.*;

public class SampleHealthCheck extends HealthCheck {

    protected SampleHealthCheck() {
        super("sample");
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy(); // To Disable Dropwizard warnings
    }
}
