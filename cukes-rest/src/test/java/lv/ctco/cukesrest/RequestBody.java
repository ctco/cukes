package lv.ctco.cukesrest;

import com.jayway.restassured.config.*;
import com.jayway.restassured.internal.*;

public class RequestBody extends RestAssuredResponseOptionsImpl {

    public RequestBody(String contentType, String content) {
        setRpr(new ResponseParserRegistrar());
        setConfig(new RestAssuredConfig());
        setContentType(contentType);
        setContent(content);
    }
}
