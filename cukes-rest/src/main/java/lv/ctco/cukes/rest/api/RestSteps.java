package lv.ctco.cukes.rest.api;

import com.google.inject.Inject;
import cucumber.api.java.en.And;
import groovy.lang.Singleton;
import groovy.util.logging.Slf4j;
import io.restassured.http.ContentType;
import lv.ctco.cukes.core.CukesDocs;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.http.facade.HttpRequestFacade;
import lv.ctco.cukes.http.facade.HttpResponseFacade;
import lv.ctco.cukes.rest.facade.RestRequestFacade;
import org.apache.commons.lang3.StringUtils;

@Singleton
@Slf4j
@InflateContext
public class RestSteps {

    @Inject
    private RestRequestFacade restRequestFacade;
    @Inject
    private HttpResponseFacade httpResponseFacade;
    @Inject
    private HttpRequestFacade httpRequestFacade;
    @Inject
    private GlobalWorldFacade world;


    @And("^HTTP request body is set:$")
    @CukesDocs("Setup request body for http request")
    public void setHttpRequestBody(String body) {
        restRequestFacade.setRequestBody(body);
    }

    @And("^HTTP \"(POST|PUT|PATCH)\" request is sent to \"(.+)\":$")
    @CukesDocs("Send http request with request body")
    public void performHttpPostRequest(String httpMethod, String url, String body) throws Throwable {
        restRequestFacade.setRequestBody(body);
        world.put("requestBody", body);
        httpResponseFacade.setResponsePrefix(StringUtils.EMPTY);
        httpResponseFacade.doRequest(httpMethod, url);
    }

    @And("^HTTP \"(POST|PUT|PATCH)\" request with (ANY|TEXT|JSON|XML|HTML|URLENC|BINARY) content type is sent to \"(.+)\":$")
    @CukesDocs("Send http request with presetup content type and request body")
    public void performHttpPostRequestWithJsonContentType(String httpMethod, ContentType contentType, String url, String body) throws Throwable {
        httpRequestFacade.contentType(contentType.toString());
        restRequestFacade.setRequestBody(body);
        world.put("requestBody", body);
        httpResponseFacade.setResponsePrefix(StringUtils.EMPTY);
        httpResponseFacade.doRequest(httpMethod, url);
    }

    @And("^HTTP \"(POST|PUT|PATCH)\" request with content type \"([^\"]+)\" is sent to \"(.+)\":$")
    @CukesDocs("Send http request with content type and request body")
    public void performHttpPostRequestWithJsonContentType(String httpMethod, String contentType, String url, String body) throws Throwable {
        httpRequestFacade.contentType(contentType);
        restRequestFacade.setRequestBody(body);
        world.put("requestBody", body);
        httpResponseFacade.setResponsePrefix(StringUtils.EMPTY);
        httpResponseFacade.doRequest(httpMethod, url);
    }
}
