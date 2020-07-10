package lv.ctco.cukes.http.mock.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.cucumber.java.en.Given;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.resources.ResourceFileReader;
import lv.ctco.cukes.http.mock.facade.HttpMockFacade;

@Singleton
@InflateContext
public class MockGivenSteps {

    @Inject
    private HttpMockFacade facade;
    @Inject
    private ResourceFileReader fileReader;

    @Given("^requesting mock for service \"([^\"]*)\" and url \"([^\"]*)\" with method \"([^\"]*)\"$")
    public void requestingMockUrlWithMethod(String mockServiceName, String url, String httpMethod) {
        facade.initMockResponse(mockServiceName, url, httpMethod);
    }

    @Given("^with header \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void withHeaderWithValue(String headerName, String headerValue) {
        facade.addRequestHeader(headerName, headerValue);
    }

    @Given("^with query parameter \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void withQueryParameterWithValue(String queryParameterName, String queryParameterValue) {
        facade.addRequestQueryParameter(queryParameterName, queryParameterValue);
    }

    @Given("^with body$")
    public void withBody(String body) {
        facade.setRequestBody(body);
    }

    @Given("^mock response will have header \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void mockResponseWillHaveHeaderWithValue(String headerName, String headerValue) {
        facade.addResponseHeader(headerName, headerValue);
    }

    @Given("^mock response will have body$")
    public void mockResponseWillHaveBody(String respondBody) {
        facade.setResponseBody(respondBody);
    }

    @Given("^mock response will have body from file \"(.+)\"$")
    public void mockResponseWillHaveBodyFromFile(String file) {
        facade.setResponseBody(this.fileReader.read(file));
    }

    @Given("^mock responds with status code \"([^\"]*)\"$")
    public void mockRespondsWithStatusCode(Integer httpRespondStatusCode) {
        facade.finishHttpMock(httpRespondStatusCode);
    }

    @Given("^mock responds with status code \"([^\"]*)\" exactly (\\d+) times?$")
    public void mockRespondsWithStatusCode(Integer httpRespondStatusCode, int times) {
        facade.finishHttpMock(httpRespondStatusCode, times);
    }
}
