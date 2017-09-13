package lv.ctco.cukes.http;

import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.ResponseParserRegistrar;
import io.restassured.internal.RestAssuredResponseOptionsImpl;

public class RequestBody extends RestAssuredResponseOptionsImpl {

    public RequestBody(String contentType, String content) {
        setRpr(new ResponseParserRegistrar());
        setConfig(new RestAssuredConfig());
        setContentType(contentType);
        setContent(content);
    }
}
