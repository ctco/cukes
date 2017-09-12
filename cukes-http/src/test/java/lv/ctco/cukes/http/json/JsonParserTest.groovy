package lv.ctco.cukes.http.json

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.runners.MockitoJUnitRunner

import static lv.ctco.cukes.http.CustomMatchers.hasSize
import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.hasEntry
import static org.junit.Assert.assertThat

@RunWith(MockitoJUnitRunner.class)
class JsonParserTest {

    @InjectMocks
    JsonParser service

    @Test
    void simpleJsonShouldBeConvertedToMap() throws Exception {
        String json = '{"hello":"world"}'
        def map = service.parsePathToValueMap(json)

        assertThat(map, allOf(
                hasSize(1),
                hasEntry('hello', 'world')
            )
        )
    }

    @Test
    void jsonStartingWithAnArrayShouldBeConvertedToMap() throws Exception {
        String json = '[{"hello":"world"}]'
        def map = service.parsePathToValueMap(json)

        assertThat(map, allOf(
                hasSize(1),
                hasEntry('[0].hello', 'world')
            )
        )
    }

    @Test
    void jsonStartingWithAnArrayShouldBeConvertedToMap_withMultipleItems() throws Exception {
        String json = '{"arr": ["1", "2"]}'
        def map = service.parsePathToValueMap(json)

        assertThat(map, allOf(
                hasSize(2),
                hasEntry("arr[0]", "1")
        ))
    }

    @Test
    void floatNumberShouldBeCorrectlyParsed() throws Exception {
        String json = '{"float": 26.505515}'
        def map = service.parsePathToValueMap(json)

        assertThat(map, hasEntry("float", "26.505515"))
    }

    @Test
    void intNumberShouldBeCorrectlyParsed() throws Exception {
        String json = '{"int":1}'
        def map = service.parsePathToValueMap(json)

        assertThat(map, hasEntry("int", "1"))
    }

    @Test
    void bigDecimalNumberShouldBeCorrectlyParsed() throws Exception {
        String json = '{"big":11111111111111111111111111}'
        def map = service.parsePathToValueMap(json)

        assertThat(map, hasEntry("big", "11111111111111111111111111"))
    }
}
