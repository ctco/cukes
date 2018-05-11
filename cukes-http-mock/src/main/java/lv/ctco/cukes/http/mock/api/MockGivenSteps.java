package lv.ctco.cukes.http.mock.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.http.mock.facade.HttpMockFacade;

@Singleton
public class MockGivenSteps {

    @Inject
    private HttpMockFacade facade;

    @Given("^requesting mock for service \"([^\"]*)\" and url \"([^\"]*)\" with method \"([^\"]*)\"$")
    public void requestingMockUrlWithMethod(String mockServiceName, String url, String httpMethod) {
        facade.initMockResponse(mockServiceName, url, httpMethod);
    }

    @Given("^with header \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void withHeaderWithValue(String headerName, String headerValue) {
        facade.addRequestHeader(headerName, headerValue);
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

    @Given("^mock responds with status code \"([^\"]*)\"$")
    public void mockRespondsWithStatusCode(Integer httpRespondStatusCode) {
        facade.finishHttpMock(httpRespondStatusCode);
    }
}
