package lv.ctco.cukesrest.internal.matchers;

import org.hamcrest.Matchers;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

public class EndsWithRegexpTest {

    @Test
    public void matchesDirectMatch() throws Exception {
        assertThat("hello", EndsWithRegexp.endsWithRegexp("hello"));
    }

    @Test
    public void matchesEndWith() throws Exception {
        assertThat("hello world", EndsWithRegexp.endsWithRegexp("world"));
    }

    @Test
    public void matchesEndWithRegexp() throws Exception {
        assertThat("hello world", EndsWithRegexp.endsWithRegexp("el.*world"));
    }

    @Test
    public void matchesNotEndWith() throws Exception {
        assertThat("hello world", Matchers.not(EndsWithRegexp.endsWithRegexp("hello")));
    }

    @Test
    public void matchesNotEndWithRegexp() throws Exception {
        assertThat("hello world", Matchers.not(EndsWithRegexp.endsWithRegexp("h.*o")));
    }

    @Test
    public void matchesLocationUrl() throws Exception {
        assertThat("http://web-dev.swissre.com:80/webapp/orx/rest/index/types/CLIENT/nodes/6f1155df-644b-4228-89af-7d24b8fe1a8d",
                     EndsWithRegexp.endsWithRegexp("/index/types/CLIENT/nodes/.+"));
    }
}