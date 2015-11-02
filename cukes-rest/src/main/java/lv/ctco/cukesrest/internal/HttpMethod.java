package lv.ctco.cukesrest.internal;

import com.jayway.restassured.response.*;
import com.jayway.restassured.specification.*;

public enum HttpMethod {
    // TODO: Refactor to normal Enum
    GET {
        @Override
        public Response doRequest(RequestSpecification when, String url) {
            return when.log().path().get(url);
        }
    },
    POST {
        @Override
        public Response doRequest(RequestSpecification when, String url) {
            return when.log().path().post(url);
        }
    }, PUT {
        @Override
        public Response doRequest(RequestSpecification when, String url) {
            return when.log().path().put(url);
        }
    }, DELETE {
        @Override
        public Response doRequest(RequestSpecification when, String url) {
            return when.log().path().delete(url);
        }
    };

    public static HttpMethod parse(String string) {
        for (HttpMethod httpMethod : values()) {
            if (httpMethod.toString().equalsIgnoreCase(string)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException();
    }

    public abstract Response doRequest(RequestSpecification when, String url);
}
