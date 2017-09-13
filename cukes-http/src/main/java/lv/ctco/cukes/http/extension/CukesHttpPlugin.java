package lv.ctco.cukes.http.extension;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.extension.CukesPlugin;

public interface CukesHttpPlugin extends CukesPlugin {

    void beforeRequest(RequestSpecification requestSpecification);

    void afterRequest(Response response);
}
