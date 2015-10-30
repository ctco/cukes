package lv.ctco.cukesrest.internal.matchers

import lv.ctco.cukesrest.RequestBody
import org.junit.Test

import static ContainsPattern.matchesPattern
import static JsonMatchers.containsValueByPath
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.core.IsNot.not

class JsonMatchersTest {

    public static final String JSON = "application/json"

    @Test
    public void "should parse simple json"() {
        def content = '{ "hello" : "world" }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("hello", equalTo("world")))
    }

    @Test
    public void "should fail on plain text"() {
        def content = '"hello" : "world"'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath("hello", equalTo("world"))))
    }

    @Test
    public void "should fail on non existing property"() {
        def content = '{ "hello" : "world" }'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath("Hello", equalTo("world"))))
    }

    @Test
    public void "should work with arrays"() {
        def content = '[ { "hello" : "world" } ]'
        assertThat(new RequestBody(JSON, content), containsValueByPath("[0].hello", equalTo("world")))
    }

    @Test
    public void "should fail for array index out of bound"() {
        def content = '[ { "hello" : "world" } ]'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath("[1].hello", equalTo("world"))))
    }

    @Test
    public void "should work for nested object"() {
        def content = '{ "nested": { "hello" : "world" } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.hello", equalTo("world")))
    }

    @Test
    public void "should work for nested array object"() {
        def content = '{ "nested": { "array": [ { "hello" : "world" } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", equalTo("world")))
    }

    @Test
    public void "should work for nested array object and contains matcher"() {
        def content = '{ "nested": { "array": [ { "hello" : "world" } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", matchesPattern("w...d")))
    }

    @Test
    public void "should work for nested array object and contains matcher that matches Integer value"() {
        def content = '{ "nested": { "array": [ { "hello" : 15 } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", matchesPattern("\\d+")))
    }

    @Test
    public void "should work for nested array object and contains matcher that matches Float value"() {
        def content = '{ "nested": { "array": [ { "hello" : 253.0 } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", matchesPattern("\\d+.\\d+")))
    }
}