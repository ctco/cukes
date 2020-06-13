package lv.ctco.cukes.http.extension;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface CukesHttpPlugin {

    void beforeRequest(RequestSpecification requestSpecification);

    void afterRequest(Response response);
}
