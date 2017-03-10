package lv.ctco.cukesrest.loadrunner.mapper;

import io.restassured.http.Headers;
import io.restassured.specification.FilterableRequestSpecification;
import lv.ctco.cukesrest.loadrunner.function.WebCustomRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static lv.ctco.cukesrest.loadrunner.CustomMatchers.stringWithLength;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebCustomRequestMapperTest {

    @InjectMocks
    WebCustomRequestMapper mapper;

    @Test
    public void snapshotNumberShouldBeLessThan10Digits() {
        FilterableRequestSpecification requestSpec = mock(FilterableRequestSpecification.class);
        when(requestSpec.getURI()).thenReturn("http://www.google.com");
        when(requestSpec.getHeaders()).thenReturn(new Headers());

        WebCustomRequest request = mapper.map(requestSpec);
        assertThat(request, hasProperty("snapshot", stringWithLength(lessThanOrEqualTo(15)))); //10 digits + t + .inf
    }
}
