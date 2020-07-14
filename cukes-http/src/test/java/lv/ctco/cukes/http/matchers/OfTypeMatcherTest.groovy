package lv.ctco.cukes.http.matchers

import lv.ctco.cukes.core.internal.matchers.JsonMatchers
import lv.ctco.cukes.http.RequestBody
import org.junit.Ignore
import org.junit.Test

import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath
import static lv.ctco.cukes.core.internal.matchers.OfTypeMatcher.ofType
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsNot.not

class OfTypeMatcherTest {

    public static JsonMatchers.ContentProvider<RequestBody> contentProvider = new JsonMatchers.ContentProvider<RequestBody>() {
        @Override
        String getValue(Object o) {
            return ((RequestBody) o).getBody()
        }

        @Override
        String getContentType(Object o) {
            return ((RequestBody) o).getContentType()
        }
    }

    public static final String JSON = "application/json"
    public static final String XML = "application/xml"

    @Test
    void "json object returned is Integer"() throws Exception {
        def content = '{"value": 1}'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "value", ofType("integer")))
    }

    @Test
    void "json object returned is Long"() throws Exception {
        def content = '{"value": 12345678987}'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "value", ofType("long")))
    }

    @Test
    void "json object returned is String"() throws Exception {
        def content = '{"value": "test"}'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "value", ofType("string")))
    }

    @Test
    void "json object returned is Float"() throws Exception {
        def content = '{"value": 1.1}'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "value", ofType("bigdecimal")))
    }

    @Test
    void "json object returned is Boolean"() throws Exception {
        def content = '{"value": true}'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "value", ofType("boolean")))
    }

    @Test
    void "json object returned is List"() throws Exception {
        def content = '{"value": [1,2]}'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "value", ofType("list")))
    }

    @Test
    void "json object returned is Map"() throws Exception {
        def content = '{"value": {"test": 1, "another": 2}}'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "value", ofType("map")))
    }

    @Test
    void "json value type doesn't match"() throws Exception {
        def content = '{"value": 1}'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath(contentProvider, "value", ofType("float"))))
    }

    @Test
    void "xml object returned is String"() throws Exception {
        def content = '<value>1</value>'
        assertThat(new RequestBody(XML, content), containsValueByPath(contentProvider, "value", ofType("string")))
    }

    @Test
    @Ignore
    // How to Handle XML?
    void "xml object returned is Map"() throws Exception {
        def content = '<value><test>1</test><another>2</another></value>'
        assertThat(new RequestBody(XML, content), containsValueByPath(contentProvider, "value", ofType("map")))
    }
}
