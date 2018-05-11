package lv.ctco.cukes.http.mock.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.http.mock.internal.MockClientServerFacade;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Singleton
public class MockRestFacade {
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

    public void setRequestBody(String body) {
        request.withBody(body);
    }

    public void addResponseHeader(String headerName, String headerValue) {
        response.withHeader(headerName, headerValue);
    }

    public void setResponseBody(String responseBody) {
        response.withBody(responseBody);
    }

    public void finishHttpMock(Integer httpRespondStatusCode) {
        response.withStatusCode(httpRespondStatusCode);
        client
            .when(request)
            .respond(response);
        request = null;
        response = null;
        client = null;
    }
}
