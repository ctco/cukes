package lv.ctco.cukescore.extension;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface CukesPlugin {

    void beforeAllTests();

    void afterAllTests();

    void beforeScenario();

    void afterScenario();

    void beforeRequest(RequestSpecification requestSpecification);

    void afterRequest(Response response);
}
