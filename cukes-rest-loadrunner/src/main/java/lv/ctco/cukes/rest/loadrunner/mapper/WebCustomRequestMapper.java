package lv.ctco.cukes.rest.loadrunner.mapper;

import com.google.inject.Inject;
import io.restassured.http.Header;
import io.restassured.specification.FilterableRequestSpecification;
import lv.ctco.cukes.rest.loadrunner.function.WebCustomRequest;
import lv.ctco.cukes.rest.loadrunner.function.WebRequestSaveParam;
import lv.ctco.cukes.rest.loadrunner.function.WebRequestSaveResponseBody;
import lv.ctco.cukes.rest.loadrunner.function.WebRequestSaveResponseHeaders;

import java.net.MalformedURLException;
import java.net.URL;

public class WebCustomRequestMapper {

    @Inject
    WebAddHeaderMapper headerMapper;

    public WebCustomRequest map(FilterableRequestSpecification requestSpec) {
        try {
            URL url = new URL(requestSpec.getURI());
            String method = String.valueOf(requestSpec.getMethod());

            WebCustomRequest request = new WebCustomRequest();
            request.setName(method + " to " + url.toString());
            //Don't URL encode LR parameter boundaries (curly braces)
            request.setUrl(url.toString().replace("%7B", "{").replace("%7D", "}"));
            request.setMethod(method);
            request.setResource("0");
            request.setSnapshot(String.format("t%d.inf", (long) (System.currentTimeMillis() % Math.pow(10, 10))));
            request.setMode(url.getProtocol());
            request.setBody(requestSpec.getBody());

            request.getBeforeFunctions().add(new WebRequestSaveParam());
            request.getBeforeFunctions().add(new WebRequestSaveResponseBody());
            request.getBeforeFunctions().add(new WebRequestSaveResponseHeaders());

            for (Header header : requestSpec.getHeaders()) {
                request.getBeforeFunctions().add(headerMapper.map(header));
            }

            return request;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
