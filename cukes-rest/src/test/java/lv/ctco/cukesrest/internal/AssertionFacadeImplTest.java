package lv.ctco.cukesrest.internal;

import com.google.common.base.*;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import org.junit.Test;

import static lv.ctco.cukesrest.CukesOptions.ASSERTS_STATUS_CODE_DISPLAY_BODY;
import static lv.ctco.cukesrest.CukesOptions.ASSERTS_STATUS_CODE_MAX_SIZE;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static lv.ctco.cukesrest.CustomMatchers.*;

public class AssertionFacadeImplTest extends IntegrationTestBase {

    AssertionFacade facade = getObjectFactory().getInstance(AssertionFacadeImpl.class);

    GlobalWorldFacade world = getObjectFactory().getInstance(GlobalWorldFacade.class);

    @Test
    public void shouldNotInflateVarName() throws Exception {
        String headerName = "name";
        ResponseFacade mock = mock(ResponseFacade.class);
        Response response = mock(Response.class);
        when(response.getHeader(anyString())).thenReturn(headerName);
        when(mock.response()).thenReturn(response);
        ((AssertionFacadeImpl) facade).facade = mock;

        world.put("id", "1");
        facade.varAssignedFromHeader("{(id)}", headerName);
        Optional<String> value = world.get("id");
        assertThat(value, equalToOptional(headerName));
    }

    @Test
    public void shouldReturnBodyWhenEnabledWithMax() {
        final ValidatableResponse validatableResponse = mock(ValidatableResponse.class);
        when(validatableResponse.statusCode(anyInt())).thenThrow(new AssertionError("404 did not match 200."));

        Response response = mock(Response.class);
        when(response.getContentType()).thenReturn("application/json");
        when(response.asString()).thenReturn("An error occurred.");
        when(response.getBody()).thenReturn(response);
        when(response.then()).thenReturn(validatableResponse);

        ResponseFacade mock = mock(ResponseFacade.class);
        when(mock.response()).thenReturn(response);

        ((AssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "100");

        validateException("404 did not match 200.\n\nBody:\nAn error occurred.");
    }

    @Test
    public void shouldReturnBodyWhenEnabledAndNoMax() {
        final ValidatableResponse validatableResponse = mock(ValidatableResponse.class);
        when(validatableResponse.statusCode(anyInt())).thenThrow(new AssertionError("404 did not match 200."));

        Response response = mock(Response.class);
        when(response.getContentType()).thenReturn("application/json");
        when(response.asString()).thenReturn("An error occurred.");
        when(response.getBody()).thenReturn(response);
        when(response.then()).thenReturn(validatableResponse);

        ResponseFacade mock = mock(ResponseFacade.class);
        when(mock.response()).thenReturn(response);

        ((AssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");

        validateException("404 did not match 200.\n\nBody:\nAn error occurred.");
    }

    @Test
    public void shouldNotReturnBodyWhenDisabled() {
        final ValidatableResponse validatableResponse = mock(ValidatableResponse.class);
        when(validatableResponse.statusCode(anyInt())).thenThrow(new AssertionError("404 did not match 200."));

        Response response = mock(Response.class);
        when(response.getContentType()).thenReturn("application/json");
        when(response.asString()).thenReturn("An error occurred.");
        when(response.getBody()).thenReturn(response);
        when(response.then()).thenReturn(validatableResponse);

        ResponseFacade mock = mock(ResponseFacade.class);
        when(mock.response()).thenReturn(response);

        ((AssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "false");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "100");

        validateException("404 did not match 200.");
    }

    @Test
    public void shouldNotReturnBodyWhenEnabledButLongerThanMaxSize() {
        final ValidatableResponse validatableResponse = mock(ValidatableResponse.class);
        when(validatableResponse.statusCode(anyInt())).thenThrow(new AssertionError("404 did not match 200."));

        Response response = mock(Response.class);
        when(response.getContentType()).thenReturn("application/json");
        when(response.asString()).thenReturn("An error occurred.");
        when(response.getBody()).thenReturn(response);
        when(response.then()).thenReturn(validatableResponse);

        ResponseFacade mock = mock(ResponseFacade.class);
        when(mock.response()).thenReturn(response);

        ((AssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "5");

        validateException("404 did not match 200.\n\nBody:\n<exceeds max size>");
    }

    @Test
    public void shouldNotReturnBodyWhenEnabledButContentTypeOctet() {
        final ValidatableResponse validatableResponse = mock(ValidatableResponse.class);
        when(validatableResponse.statusCode(anyInt())).thenThrow(new AssertionError("404 did not match 200."));

        Response response = mock(Response.class);
        when(response.getContentType()).thenReturn("application/octet-stream");
        when(response.asString()).thenReturn("An error occurred.");
        when(response.getBody()).thenReturn(response);
        when(response.then()).thenReturn(validatableResponse);

        ResponseFacade mock = mock(ResponseFacade.class);
        when(mock.response()).thenReturn(response);

        ((AssertionFacadeImpl) facade).facade = mock;
        world.put(ASSERTS_STATUS_CODE_DISPLAY_BODY, "true");
        world.put(ASSERTS_STATUS_CODE_MAX_SIZE, "5000");

        validateException("404 did not match 200.\n\nBody:\n<binary>");
    }

    private void validateException(String expectedMessage) {
        try {
            facade.statusCodeIs(200);
            fail("Exception expected!");
        } catch (AssertionError error) {
            assertEquals(expectedMessage, error.getMessage());
        }
    }
}
