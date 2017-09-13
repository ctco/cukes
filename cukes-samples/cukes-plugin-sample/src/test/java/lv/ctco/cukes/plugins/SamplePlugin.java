package lv.ctco.cukes.plugins;

import lv.ctco.cukes.core.extension.CukesPlugin;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamplePlugin implements CukesPlugin {

    private static final Logger logger = LoggerFactory.getLogger(SamplePlugin.class);

    private StopWatch stopWatch = new StopWatch();

    @Override
    public void beforeAllTests() {

    }

    @Override
    public void afterAllTests() {

    }

    @Override
    public void beforeScenario() {
        stopWatch.reset();
        stopWatch.start();
    }

    @Override
    public void afterScenario() {
        stopWatch.stop();
        long executionTime = stopWatch.getTime();
        logger.info("Scenario executed in {} ms", executionTime);
    }
}
