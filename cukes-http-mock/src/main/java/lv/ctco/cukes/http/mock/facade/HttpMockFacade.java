package lv.ctco.cukes.http.mock.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.http.mock.internal.MockClientServerFacade;
import org.apache.commons.lang3.ArrayUtils;
import org.mockserver.client.MockServerClient;
import org.mockserver.matchers.Times;
import org.mockserver.mock.Expectation;
import org.mockserver.model.Body;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.StringBody;

import java.util.Arrays;
import java.util.Optional;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Singleton
public class HttpMockFacade {
    @Inject
    private MockClientServerFacade mockClientServerFacade;

    private MockServerClient client;
    private HttpRequest request;
    private HttpResponse response;

    public void initMockResponse(String serviceName, String url, String httpMethod) {
        client = mockClientServerFacade.getMockServerClient(serviceName);
        request = request(url).withMethod(httpMethod);
        response = response();
    }

    public void addRequestHeader(String headerName, String headerValue) {
        request.withHeader(headerName, headerValue);
    }

    public void addRequestQueryParameter(String queryParameterName, String queryParameterValue) {
        request.withQueryStringParameter(queryParameterName, queryParameterValue);
    }

    public void setRequestBody(String body) {
        this.setRequestBody(new StringBody(body));
    }

    public void setRequestBody(Body body) {
        request.withBody(body);
    }

    public void addResponseHeader(String headerName, String headerValue) {
        response.withHeader(headerName, headerValue);
    }

    public void setResponseBody(String responseBody) {
        response.withBody(responseBody);
    }

    public void finishHttpMock(Integer httpRespondStatusCode) {
        finishHttpMock(httpRespondStatusCode, Times.unlimited());
    }

    protected void finishHttpMock(Integer httpRespondStatusCode, Times times) {
        Expectation[] activeExpectations = client.retrieveActiveExpectations(request);
        Optional<Expectation> defaultExpectation = Arrays.stream(activeExpectations).
            filter(exp -> exp.getTimes().equals(Times.unlimited())).
            findAny();

        client.clear(request);
        restoreNonDefaultExpectations(activeExpectations);

        response.withStatusCode(httpRespondStatusCode);
        client
            .when(request, times)
            .respond(response);
        if (defaultExpectation.isPresent() && !times.equals(Times.unlimited())) {
            Expectation exp = defaultExpectation.get();
            restoreExpectation(exp);
        }

        request = null;
        response = null;
        client = null;
    }

    private void restoreNonDefaultExpectations(Expectation[] activeExpectations) {
        if (ArrayUtils.isNotEmpty(activeExpectations)) {
            Arrays.stream(activeExpectations).filter(exp -> !exp.getTimes().equals(Times.unlimited())).
                forEach(this::restoreExpectation);
        }
    }

    private void restoreExpectation(Expectation exp) {
        client.when(exp.getHttpRequest(), exp.getTimes(), exp.getTimeToLive()).respond(exp.getHttpResponse());
    }

    public void finishHttpMock(Integer httpRespondStatusCode, int times) {
        finishHttpMock(httpRespondStatusCode, Times.exactly(times));
    }

}
