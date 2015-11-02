package lv.ctco.cukesrest.internal.json

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.runners.MockitoJUnitRunner

import static lv.ctco.cukesrest.CustomMatchers.hasSize
import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.hasEntry
import static org.junit.Assert.assertThat

@RunWith(MockitoJUnitRunner.class)
class JsonParserTest {

    @InjectMocks
    JsonParser service;

    @Test
    public void simpleJsonShouldBeConvertedToMap() throws Exception {
        String json = '{"hello":"world"}';
        def map = service.convertToPathToValueMap(json);

        assertThat(map, allOf(
                hasSize(1),
                hasEntry('hello', 'world')
            )
        );
    }

    @Test
    public void jsonStartingWithAnArrayShouldBeConvertedToMap() throws Exception {
        String json = '[{"hello":"world"}]';
        def map = service.convertToPathToValueMap(json);

        assertThat(map, allOf(
                hasSize(1),
                hasEntry('[0].hello', 'world')
            )
        );
    }
}
