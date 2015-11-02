package lv.ctco.cukesrest.loadrunner.mapper;

import com.google.inject.*;
import com.jayway.restassured.filter.*;
import com.jayway.restassured.response.*;
import com.jayway.restassured.specification.*;
import lv.ctco.cukesrest.loadrunner.function.*;

import java.net.*;

public class WebCustomRequestMapper {

    @Inject
    WebAddHeaderMapper headerMapper;

    public WebCustomRequest map(FilterableRequestSpecification requestSpec, FilterContext ctx) {
        try {
            URL url = new URL(ctx.getCompleteRequestPath());
            String method = String.valueOf(ctx.getRequestMethod());

            WebCustomRequest request = new WebCustomRequest();
            request.setName(method + " to " + url.toString());
            request.setUrl(url.toString());
            request.setMethod(method);
            request.setResource("0");
            request.setSnapshot(String.format("t%d.inf", (long) (System.currentTimeMillis() % Math.pow(10, 10))));
            request.setMode(url.getProtocol());
            request.setBody((String) requestSpec.getBody());

            for (Header header : requestSpec.getHeaders()) {
                request.getBeforeFunctions().add(headerMapper.map(header));
            }

            return request;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
