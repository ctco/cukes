package lv.ctco.cukesrest.internal;

import com.jayway.restassured.response.*;
import com.jayway.restassured.specification.*;
import lv.ctco.cukesrest.*;

public enum HttpMethod {
    GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH;

    public static HttpMethod parse(String string) {
        for (HttpMethod httpMethod : values()) {
            if (httpMethod.toString().equalsIgnoreCase(string)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException();
    }

    public Response doRequest(RequestSpecification when, String url) {
        switch (this) {
            case GET: return when.get(url);
            case POST: return when.post(url);
            case PUT: return when.put(url);
            case DELETE: return when.delete(url);
            case OPTIONS: return when.options(url);
            case HEAD: return when.head(url);
            case PATCH: return when.patch(url);
        }
        throw new CukesRuntimeException("This Http Method is nos supported");
    }
}
