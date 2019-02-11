package lv.ctco.cukes.soap.sample;

import lv.ctco.cukes.core.extension.CukesPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class CukesBootstrap implements CukesPlugin {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void beforeAllTests() {
        this.applicationContext = SpringApplication.run(Application.class);
    }

    @Override
    public void afterAllTests() {
        applicationContext.close();
    }

    @Override
    public void beforeScenario() {

    }

    @Override
    public void afterScenario() {

    }
}
