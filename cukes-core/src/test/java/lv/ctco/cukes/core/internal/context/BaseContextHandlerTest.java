package lv.ctco.cukes.core.internal.context;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BaseContextHandlerTest {

    @InjectMocks
    ContextCapturer capturer;

    @Test
    public void shouldExtractNoGroupsInPattern() throws Exception {
        List<String> groups = capturer.extractGroups("(hello)");
        assertThat(groups, is(empty()));
    }

    @Test
    public void shouldExtractSingleGroupInPattern() throws Exception {
        List<String> groups = capturer.extractGroups("{(hello)}");
        assertThat(groups, contains("hello"));
    }

    @Test
    public void shouldExtractTwoGroupsInPattern() throws Exception {
        List<String> groups = capturer.extractGroups("{(hello)}, {(world)}");
        assertThat(groups, contains("hello", "world"));
    }

    @Test
    public void shouldNotExtractGroupsInPatternWithSpacesInName() throws Exception {
        List<String> groups = capturer.extractGroups("{(hello world)}");
        assertThat(groups, is(empty()));
    }

    @Test
    public void shouldExtractGroupsInPatternWithUnderscoreInName() throws Exception {
        List<String> groups = capturer.extractGroups("{(hello_world)}");
        assertThat(groups, contains("hello_world"));
    }

    @Test
    public void shouldExtractDotSeparatedName() throws Exception {
        List<String> groups = capturer.extractGroups("{(hello.world)}");
        assertThat(groups, contains("hello.world"));
    }
}
