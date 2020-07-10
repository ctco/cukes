package lv.ctco.cukes.core.internal.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(MockitoJUnitRunner.class)
public class BaseContextHandlerTest {

    @InjectMocks
    ContextCapturer capturer;

    @Test
    public void shouldExtractNoGroupsInPattern() {
        List<String> groups = capturer.extractGroups("(hello)");
        assertThat(groups, is(empty()));
    }

    @Test
    public void shouldExtractSingleGroupInPattern() {
        List<String> groups = capturer.extractGroups("{(hello)}");
        assertThat(groups, contains("hello"));
    }

    @Test
    public void shouldExtractTwoGroupsInPattern() {
        List<String> groups = capturer.extractGroups("{(hello)}, {(world)}");
        assertThat(groups, contains("hello", "world"));
    }

    @Test
    public void shouldNotExtractGroupsInPatternWithSpacesInName() {
        List<String> groups = capturer.extractGroups("{(hello world)}");
        assertThat(groups, is(empty()));
    }

    @Test
    public void shouldExtractGroupsInPatternWithUnderscoreInName() {
        List<String> groups = capturer.extractGroups("{(hello_world)}");
        assertThat(groups, contains("hello_world"));
    }

    @Test
    public void shouldExtractDotSeparatedName() {
        List<String> groups = capturer.extractGroups("{(hello.world)}");
        assertThat(groups, contains("hello.world"));
    }
}
