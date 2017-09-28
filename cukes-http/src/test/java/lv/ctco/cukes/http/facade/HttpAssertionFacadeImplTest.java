package lv.ctco.cukes.http.facade;

import com.google.common.base.Optional;
import cucumber.api.java.ObjectFactory;
import io.restassured.internal.ResponseParserRegistrar;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.internal.http.HttpResponseDecorator;
import io.restassured.response.Response;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.di.SingletonObjectFactory;
import lv.ctco.cukes.http.CustomMatchers;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.ProtocolVersion;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;

import java.util.Locale;

import static lv.ctco.cukes.core.CukesOptions.ASSERTS_STATUS_CODE_DISPLAY_BODY;
import static lv.ctco.cukes.core.CukesOptions.ASSERTS_STATUS_CODE_MAX_SIZE;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpAssertionFacadeImplTest {

    private ObjectFactory objectFactory = SingletonObjectFactory.instance();

    HttpAssertionFacade facade = objectFactory.getInstance(HttpAssertionFacadeImpl.class);

    GlobalWorldFacade world = objectFactory.getInstance(GlobalWorldFacade.class);

    @Test
    public void shouldNotInflateVarName() throws Exception {
        String headerName = "name";
        HttpResponseFacade mock = mock(HttpResponseFacade.class);
        Response response = mock(Response.class);
        when(response.getHeader(anyString())).thenReturn(headerName);
        when(mock.response()).thenReturn(response);
        ((HttpAssertionFacadeImpl) facade).facade = mock;

        world.put("id", "1");
        facade.varAssignedFromHeader("{(id)}", headerName);
        Optional<String> value = world.get("id");
        assertThat(value, CustomMatchers.equalToOptional(headerName));
    }

    @Test
    public void shouldReturnBodyWhenEnabledWithMax() {
        String body = "{\n" +
            "  \"error\": \"not found\"\n" +
            "}";

        HttpResponseFacade mock = mock(HttpResponseFacade.class);
        when(mock.response()).thenReturn(generateResponse(
            "application/json",
            404,
            body.getBytes()));

        ((HttpAssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "100");

        validateException(
            200,
            "1 expectation failed.\n" +
                "Expected status code \"200\" but was \"404\" with body:\n" +
                "\"\"\"\n" +
                body +
                "\n\"\"\".\n");
    }

    @Test
    public void shouldReturnBodyWhenEnabledAndNoMax() {
        String body = "{\n" +
            "  \"error\": \"not found\"\n" +
            "}";

        HttpResponseFacade mock = mock(HttpResponseFacade.class);
        when(mock.response()).thenReturn(generateResponse(
            "application/json",
            404,
            body.getBytes()));

        ((HttpAssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");

        validateException(
            200,
            "1 expectation failed.\n" +
                "Expected status code \"200\" but was \"404\" with body:\n" +
                "\"\"\"\n" +
                body +
                "\n\"\"\".\n");
    }

    @Test
    public void shouldNotReturnBodyWhenDisabled() {
        String body = "{\n" +
            "  \"error\": \"not found\"\n" +
            "}";

        HttpResponseFacade mock = mock(HttpResponseFacade.class);
        when(mock.response()).thenReturn(generateResponse(
            "application/json",
            404,
            body.getBytes()));

        ((HttpAssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "false");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "100");

        validateException(
            200,
            "1 expectation failed.\n" +
                "Expected status code \"200\" but was \"404\".\n");
    }

    @Test
    public void shouldNotReturnBodyWhenEnabledButLongerThanMaxSize() {
        String body = "{\n" +
            "  \"error\": \"not found\"\n" +
            "}";

        HttpResponseFacade mock = mock(HttpResponseFacade.class);
        when(mock.response()).thenReturn(generateResponse(
            "application/json",
            404,
            body.getBytes()));

        ((HttpAssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "5");


        validateException(
            200,
            "1 expectation failed.\n" +
                "Expected status code \"200\" but was \"404\" with body <exceeding max size to show>.\n");
    }

    @Test
    public void shouldNotReturnBodyWhenEnabledButContentTypeOctet() {
        byte[] body = RandomUtils.nextBytes(20);

        HttpResponseFacade mock = mock(HttpResponseFacade.class);
        when(mock.response()).thenReturn(generateResponse(
            "application/octet-stream",
            404,
            body));

        ((HttpAssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "5000");

        validateException(
            200,
            "1 expectation failed.\n" +
                "Expected status code \"200\" but was \"404\" with body <binary>.\n");
    }

    private Response generateResponse(String contentType, int status, byte[] content) {
        final BasicStatusLine statusLine = new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1),
            status,
            EnglishReasonPhraseCatalog.INSTANCE.getReason(status, Locale.ENGLISH));

        final BasicHttpResponse httpResponse = new BasicHttpResponse(statusLine);
        httpResponse.addHeader("Content-Type", contentType);

        final HttpResponseDecorator httpResponseDecorator = new HttpResponseDecorator(httpResponse, content);
        final RestAssuredResponseImpl restResponse = new RestAssuredResponseImpl();
        restResponse.setStatusCode(status);
        restResponse.parseResponse(
            httpResponseDecorator,
            content,
            false,
            new ResponseParserRegistrar()
        );

        return restResponse;
    }

    /**
     * @param failureStatusCode - status code that should generate a failure
     */
    private void validateException(int failureStatusCode, String expectedMessage) {
        try {
            facade.statusCodeIs(failureStatusCode);
            fail("Exception expected!");
        } catch (AssertionError error) {
            assertEquals(expectedMessage, error.getMessage());
        }
    }
}
