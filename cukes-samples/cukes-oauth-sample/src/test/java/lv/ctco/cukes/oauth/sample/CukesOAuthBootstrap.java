package lv.ctco.cukes.oauth.sample;

import lv.ctco.cukes.core.extension.CukesPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class CukesOAuthBootstrap implements CukesPlugin {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void beforeAllTests() {
        applicationContext = SpringApplication.run(OAuthSampleApplication.class);
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
