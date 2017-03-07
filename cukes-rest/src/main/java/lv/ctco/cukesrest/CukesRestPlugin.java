package lv.ctco.cukesrest;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public interface CukesRestPlugin {

    void beforeAllTests();

    void afterAllTests();

    void beforeScenario();

    void afterScenario();

    void beforeRequest(RequestSpecification requestSpecification);

    void afterRequest(Response response);
}
