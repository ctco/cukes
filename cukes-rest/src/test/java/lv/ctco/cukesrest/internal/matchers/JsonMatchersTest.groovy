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
    public static final String XML = "application/xml"
    public static final String HTML = "text/html"
    public static final String TEXT = "text/plain"

    @Test
    public void "parse simple JSON"() {
        def content = '{ "hello" : "world" }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("hello", equalTo("world")))
    }

    @Test
    public void "parse simple XML"() {
        def content = '<hello>world</hello>'
        assertThat(new RequestBody(XML, content), containsValueByPath("hello", equalTo("world")))
    }

    @Test
    public void "parse simple XML when content is HTML"() {
        def content = '<hello>world</hello>'
        assertThat(new RequestBody(HTML, content), containsValueByPath("hello", equalTo("world")))
    }

    @Test
    public void "parse simple HTML"() {
        def content = '<html><body><div>world</div></body></html>'
        assertThat(new RequestBody(HTML, content), containsValueByPath("div", equalTo("world")))
    }

    @Test
    public void "fail on plain TEXT with JSON content header"() {
        def content = '"hello" : "world"'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath("hello", equalTo("world"))))
    }

    @Test
    public void "fail on plain TEXT with TEXT content header"() {
        def content = '"hello" : "world"'
        assertThat(new RequestBody(TEXT, content), not(containsValueByPath("hello", equalTo("world"))))
    }

    @Test
    public void "fail on non existing property"() {
        def content = '{ "hello" : "world" }'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath("Hello", equalTo("world"))))
    }

    @Test
    public void "arrays in JSON"() {
        def content = '[ { "hello" : "world" } ]'
        assertThat(new RequestBody(JSON, content), containsValueByPath("[0].hello", equalTo("world")))
    }

    @Test
    public void "fail for array index out of bound in JSON"() {
        def content = '[ { "hello" : "world" } ]'
        assertThat(new RequestBody(JSON, content), not(containsValueByPath("[1].hello", equalTo("world"))))
    }

    @Test
    public void "nested object in JSON"() {
        def content = '{ "nested": { "hello" : "world" } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.hello", equalTo("world")))
    }

    @Test
    public void "nested array object in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : "world" } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", equalTo("world")))
    }

    @Test
    public void "nested array object in XML"() {
        def content =
                '<nested>' +
                    '<array><hello>world</hello></array>' +
                    '<array><hello>foo</hello></array>' +
                '</nested>'
        assertThat(new RequestBody(XML, content), containsValueByPath("nested.array[1].hello", equalTo("foo")))
    }

    @Test
    public void "nested array object and contains matcher in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : "world" } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", matchesPattern("w...d")))
    }

    @Test
    public void "nested array object and contains matcher that matches Integer value in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : 15 } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", matchesPattern("\\d+")))
    }

    @Test
    public void "nested array object and contains matcher that matches Float value in JSON"() {
        def content = '{ "nested": { "array": [ { "hello" : 253.0 } ] } }'
        assertThat(new RequestBody(JSON, content), containsValueByPath("nested.array[0].hello", matchesPattern("\\d+.\\d+")))
    }

    @Test
    public void "xml doctype support"() {
        def content = '<?xml version="1.0" encoding="UTF-8"?>' +
                '<!DOCTYPE note SYSTEM "Note.dtd">' +
                '<hi><from><world>Hello world</world></from></hi>'
        assertThat(new RequestBody(XML, content), containsValueByPath("hi.from.world", equalTo("Hello world")))
    }

    @Test
    public void "html doctype and xmlns support"() {
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
        assertThat(new RequestBody(HTML, content), containsValueByPath("html.body.section", equalTo("Hello world")))
    }
}
