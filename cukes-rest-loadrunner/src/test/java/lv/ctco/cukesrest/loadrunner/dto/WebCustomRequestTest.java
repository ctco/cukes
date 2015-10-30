package lv.ctco.cukesrest.loadrunner.dto;

import lv.ctco.cukesrest.loadrunner.function.WebCustomRequest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class WebCustomRequestTest {

    @Test
    public void formatShouldEscapeDoubleQuotes() throws Exception {
        WebCustomRequest request = new WebCustomRequest() {{
            setBody("hello \"world\"");
        }};
        assertThat(request.format(), containsString("hello \\\"world\\\""));
    }
}