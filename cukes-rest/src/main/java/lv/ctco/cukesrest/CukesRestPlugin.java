package lv.ctco.cukesrest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface CukesRestPlugin {

    void beforeAllTests();

    void afterAllTests();

    void beforeScenario();

    void afterScenario();

    void beforeRequest(RequestSpecification requestSpecification);

    void afterRequest(Response response);
}
