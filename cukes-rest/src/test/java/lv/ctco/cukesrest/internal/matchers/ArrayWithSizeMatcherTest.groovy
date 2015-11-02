package lv.ctco.cukesrest.internal.matchers

import lv.ctco.cukesrest.RequestBody
import org.junit.Test

import static ArrayWithSizeMatcher.arrayWithSize
import static JsonMatchers.containsValueByPath
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsNot.not

public class ArrayWithSizeMatcherTest {

    @Test
    public void "should work for arrays with size 2"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath("array", arrayWithSize("2")))
    }

    @Test
    public void "should work for empty array"() throws Exception {
        def content = '{"array": [] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath("array", arrayWithSize("0")))
    }

    @Test
    public void "should fail for non-empty arrays"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), not(containsValueByPath("array", arrayWithSize("0"))))
    }

    @Test
    public void "should work for great or equals sizes"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath("array", arrayWithSize(">1")))
    }

    @Test
    public void "should work for great sizes"() throws Exception {
        def content = '{"array": [1,2] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath("array", arrayWithSize(">=2")))
    }

    @Test
    public void "should work for less sizes"() throws Exception {
        def content = '{"array": [1,2,3] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath("array", arrayWithSize("<4")))
    }

    @Test
    public void "should work for less or equal sizes"() throws Exception {
        def content = '{"array": [1,2,3] }'
        assertThat(new RequestBody("application/json", content), containsValueByPath("array", arrayWithSize("<=3")))
    }

    @Test
    public void "should work for less or equal sizes in xml"() throws Exception {
        def content = '<array><item>1</item><item>2</item><item>3</item></array>'
        assertThat(new RequestBody("application/xml", content), containsValueByPath("array.item", arrayWithSize("<=3")))
    }
}