package lv.ctco.cukes.soap.sample;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class CukesBootstrap implements CukesHttpPlugin {

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

    @Override
    public void beforeRequest(RequestSpecification requestSpecification) {

    }

    @Override
    public void afterRequest(Response response) {

    }
}
