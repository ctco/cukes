package lv.ctco.cukes.soap.sample;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;

public class TestPlugin implements CukesHttpPlugin, CukesPlugin {

    @Override
    public void beforeAllTests() {
        System.out.println("TestPlugin.beforeAllTests");
    }

    @Override
    public void afterAllTests() {
        System.out.println("TestPlugin.afterAllTests");
    }

    @Override
    public void beforeScenario() {
        System.out.println("TestPlugin.beforeScenario");
    }

    @Override
    public void afterScenario() {
        System.out.println("TestPlugin.afterScenario");
    }

    @Override
    public void beforeRequest(RequestSpecification requestSpecification) {
        System.out.println("TestPlugin.beforeRequest");
    }

    @Override
    public void afterRequest(Response response) {
        System.out.println("TestPlugin.afterRequest");
    }
}
