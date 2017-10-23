package lv.ctco.cukes.http.matchers

import lv.ctco.cukes.core.internal.matchers.JsonMatchers
import lv.ctco.cukes.http.RequestBody
import org.junit.Test

import static lv.ctco.cukes.core.internal.matchers.ArrayWithSizeMatcher.arrayWithSize
import static lv.ctco.cukes.core.internal.matchers.JsonMatchers.containsValueByPath
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsNot.not

class ArrayWithSizeMatcherTest {

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
    void "arrays with size 2"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath(contentProvider, "array", arrayWithSize("2")))
    }

    @Test
    void "empty array"() throws Exception {
        def content = '{"array": [] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath(contentProvider, "array", arrayWithSize("0")))
    }

    @Test
    void "fail for non-empty arrays"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), not(containsValueByPath(contentProvider, "array", arrayWithSize("0"))))
    }

    @Test
    void "great or equals sizes"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath(contentProvider, "array", arrayWithSize(">1")))
    }

    @Test
    void "great sizes"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath(contentProvider, "array", arrayWithSize(">=2")))
    }

    @Test
    void "less sizes"() throws Exception {
        def content = '{"array": [1,2,3] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath(contentProvider, "array", arrayWithSize("<4")))
    }

    @Test
    void "less or equal sizes"() throws Exception {
        def content = '{"array": [1,2,3] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath(contentProvider, "array", arrayWithSize("<=3")))
    }

    @Test
    void "less or equal sizes in xml"() throws Exception {
        def content = '<array><item>1</item><item>2</item><item>3</item></array>'
        assertThat(new RequestBody("application/xml", content), containsValueByPath(contentProvider, "array.item", arrayWithSize("<=3")))
    }
}
