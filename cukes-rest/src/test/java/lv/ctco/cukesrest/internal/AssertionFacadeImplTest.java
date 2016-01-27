package lv.ctco.cukesrest.internal;

import com.jayway.restassured.response.Response;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssertionFacadeImplTest extends IntegrationTestBase {

    AssertionFacade facade = getInjector().getInstance(AssertionFacadeImpl.class);

    GlobalWorldFacade world = getInjector().getInstance(GlobalWorldFacade.class);

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
        String value = world.get("id");
        assertThat(value, equalTo(headerName));
    }
}
