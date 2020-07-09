package lv.ctco.cukes.core.internal.context;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContextCapturerTest {

    @Spy
    @InjectMocks
    ContextCapturer capturer = new ContextCapturer();

    @Mock
    GlobalWorldFacade world;

    @Test
    public void shouldTransformPatternToValidRegex() {
        String regex = capturer.transformToRegex("{(hello)} world");
        assertThat(regex, equalTo("(.*) world"));
    }

    @Test
    public void shouldTransformMultiplePatternToValidRegex() {
        String regex = capturer.transformToRegex("{(hello)} {(world)}");
        assertThat(regex, equalTo("(.*) (.*)"));
    }

    @Test
    public void shouldCaptureValuesFromSimplePattern() {
        capturer.captureValuesFromPattern("(.*) world", Lists.newArrayList("hello"), "Hi world");
        verify(world).put("hello", "Hi");
    }

    @Test
    public void shouldCaptureValuesFromMinimalPattern() {
        capturer.captureValuesFromPattern("(.*)", Lists.newArrayList("hello"), "world");
        verify(world).put("hello", "world");
    }

    @Test
    public void shouldNotInvokeCaptureValuesFromPatternIfNoGroupsFound() {
        capturer.capture("hello", "world");
        verify(capturer, never()).captureValuesFromPattern(anyString(), anyList(), anyString());
    }

    @Test
    public void shouldInvokeCaptureValuesFromPatternIfAtLeastOneGroupFound() {
        capturer.capture("{(hello)}", "world");
        verify(capturer).captureValuesFromPattern(anyString(), anyList(), anyString());
    }

    @Test
    public void shouldNotInvokeCaptureValuesFromPatternIfRegexDoesNotMatchValue() {
        capturer.capture("{(hello)} Riga", "hello world");
        verify(capturer, never()).captureValuesFromPattern(anyString(), anyList(), anyString());
    }
}
