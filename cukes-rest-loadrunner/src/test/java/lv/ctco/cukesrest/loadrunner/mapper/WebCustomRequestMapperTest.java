package lv.ctco.cukesrest.loadrunner.mapper;

import com.jayway.restassured.filter.*;
import com.jayway.restassured.response.*;
import com.jayway.restassured.specification.*;
import lv.ctco.cukesrest.loadrunner.function.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import static lv.ctco.cukesrest.loadrunner.CustomMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebCustomRequestMapperTest {

    @InjectMocks
    WebCustomRequestMapper mapper;

    @Test
    public void snapshotNumberShouldBeLessThan10Digits() {
        FilterContext ctx = mock(FilterContext.class);
        when(ctx.getCompleteRequestPath()).thenReturn("http://www.google.com");

        FilterableRequestSpecification requestSpec = mock(FilterableRequestSpecification.class);
        when(requestSpec.getHeaders()).thenReturn(new Headers());

        WebCustomRequest request = mapper.map(requestSpec, ctx);
        assertThat(request, hasProperty("snapshot", stringWithLength(lessThanOrEqualTo(15)))); //10 digits + t + .inf
    }
}