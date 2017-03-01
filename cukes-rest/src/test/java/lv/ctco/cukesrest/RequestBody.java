package lv.ctco.cukesrest;

import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.internal.ResponseParserRegistrar;
import com.jayway.restassured.internal.RestAssuredResponseOptionsImpl;

public class RequestBody extends RestAssuredResponseOptionsImpl {

    public RequestBody(String contentType, String content) {
        setRpr(new ResponseParserRegistrar());
        setConfig(new RestAssuredConfig());
        setContentType(contentType);
        setContent(content);
    }
}
