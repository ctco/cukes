package lv.ctco.cukes.rest.loadrunner.dto;

import lv.ctco.cukes.rest.loadrunner.function.WebCustomRequest;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class WebCustomRequestTest {

    @Test
    public void formatShouldEscapeDoubleQuotes() {
        WebCustomRequest request = new WebCustomRequest() {{
            setBody("hello \"world\"");
        }};
        assertThat(request.format(), containsString("hello \\\"world\\\""));
    }
}
