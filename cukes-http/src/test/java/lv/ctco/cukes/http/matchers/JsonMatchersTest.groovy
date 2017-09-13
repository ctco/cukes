package lv.ctco.cukes.http.matchers

import lv.ctco.cukes.core.internal.matchers.JsonMatchers
import lv.ctco.cukes.http.RequestBody
import org.junit.Test

import static lv.ctco.cukes.core.internal.matchers.ContainsPattern.matchesPattern
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.core.IsNot.not

class JsonMatchersTest {

    public static final String JSON = "application/json"
    public static final String XML = "application/xml"
    public static final String HTML = "text/html"
    public static final String TEXT = "text/plain"
    
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

    @Test
    void "parse simple JSON"() {
        def content = '{ "hello" : "world" }'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "hello", equalTo("world")))
    }

    @Test
    void "parse simple XML"() {
        def content = '<hello>world</hello>'
        assertThat(new RequestBody(XML, content), containsValueByPath(contentProvider, "hello", equalTo("world")))
    }

    @Test
    void "parse simple XML when content is HTML"() {
        def content = '<hello>world</hello>'
        assertThat(new RequestBody(HTML, content), containsValueByPath(contentProvider, "hello", equalTo("world")))
    }

    @Test
    void "parse simple HTML"() {
        def content = '<html><body><div>world</div></body></html>'
        assertThat(new RequestBody(HTML, content), containsValueByPath(contentProvider, "div", equalTo("world")))
    }

    @Test
    void "fail on plain TEXT with JSON content header"() {
        def content = '"hello" : "world"'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath(contentProvider, "hello", equalTo("world"))))
    }

    @Test
    void "fail on plain TEXT with TEXT content header"() {
        def content = '"hello" : "world"'
        assertThat(new RequestBody(TEXT, content), not(containsValueByPath(contentProvider, "hello", equalTo("world"))))
    }

    @Test
    void "fail on non existing property"() {
        def content = '{ "hello" : "world" }'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath(contentProvider, "Hello", equalTo("world"))))
    }

    @Test
    void "arrays in JSON"() {
        def content = '[ { "hello" : "world" } ]'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "[0].hello", equalTo("world")))
    }

    @Test
    void "fail for array index out of bound in JSON"() {
        def content = '[ { "hello" : "world" } ]'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath(contentProvider, "[1].hello", equalTo("world"))))
    }

    @Test
    void "nested object in JSON"() {
        def content = '{ "nested": { "hello" : "world" } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "nested.hello", equalTo("world")))
    }

    @Test
    void "nested array object in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : "world" } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "nested.array[0].hello", equalTo("world")))
    }

    @Test
    void "nested array object in XML"() {
        def content =
                '<nested>' +
                    '<array><hello>world</hello></array>' +
                    '<array><hello>foo</hello></array>' +
                '</nested>'
        assertThat(new RequestBody(XML, content), containsValueByPath(contentProvider, "nested.array[1].hello", equalTo("foo")))
    }

    @Test
    void "nested array object and contains matcher in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : "world" } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "nested.array[0].hello", matchesPattern("w...d")))
    }

    @Test
    void "nested array object and contains matcher that matches Integer value in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : 15 } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "nested.array[0].hello", matchesPattern("\\d+")))
    }

    @Test
    void "nested array object and contains matcher that matches Float value in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : 253.0 } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath(contentProvider, "nested.array[0].hello", matchesPattern("\\d+.\\d+")))
    }

    @Test
    void "xml doctype support"() {
        def content = '<?xml version="1.0" encoding="UTF-8"?>' +
                '<!DOCTYPE note SYSTEM "Note.dtd">' +
                '<hi><from><world>Hello world</world></from></hi>'
        assertThat(new RequestBody(XML, content), containsValueByPath(contentProvider, "hi.from.world", equalTo("Hello world")))
    }

    @Test
    void "html doctype and xmlns support"() {
        def content = '<?xml version="1.0" encoding="UTF-8" ?>' +
                '<!DOCTYPE html>' +
                '<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"' +
                    'dir="ltr">' +
                '<head>\n' +
                '<meta name="description"' +
                    'content="application/xhtml+xml; charset=UTF-8" />' +
                '<title>Get an object</title>' +
                '</head>' +
                '<body>' +
                    '<section>Hello world</section>' +
                '</body>'
        assertThat(new RequestBody(HTML, content), containsValueByPath(contentProvider, "html.body.section", equalTo("Hello world")))
    }
}
