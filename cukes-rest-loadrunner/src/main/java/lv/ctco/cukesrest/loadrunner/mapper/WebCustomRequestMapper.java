package lv.ctco.cukesrest.loadrunner.mapper;

import com.google.inject.Inject;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import lv.ctco.cukesrest.loadrunner.function.WebCustomRequest;

import java.net.MalformedURLException;
import java.net.URL;

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
            request.setSnapshot(String.format("t%d.inf",  (long)(System.currentTimeMillis()%Math.pow(10, 10))));
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
