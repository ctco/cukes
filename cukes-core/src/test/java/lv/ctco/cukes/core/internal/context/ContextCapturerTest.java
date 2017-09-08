package lv.ctco.cukes.core.internal.context;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContextCapturerTest {

    @Spy
    @InjectMocks
    ContextCapturer capturer = new ContextCapturer();

    @Mock
    GlobalWorldFacade world;

    @Test
    public void shouldTransformPatternToValidRegex() throws Exception {
        String regex = capturer.transformToRegex("{(hello)} world");
        assertThat(regex, equalTo("(.*) world"));
    }

    @Test
    public void shouldTransformMultiplePatternToValidRegex() throws Exception {
        String regex = capturer.transformToRegex("{(hello)} {(world)}");
        assertThat(regex, equalTo("(.*) (.*)"));
    }

    @Test
    public void shouldCaptureValuesFromSimplePattern() throws Exception {
        capturer.captureValuesFromPattern("(.*) world", Lists.newArrayList("hello"), "Hi world");
        verify(world).put("hello", "Hi");
    }

    @Test
    public void shouldCaptureValuesFromMinimalPattern() throws Exception {
        capturer.captureValuesFromPattern("(.*)", Lists.newArrayList("hello"), "world");
        verify(world).put("hello", "world");
    }

    @Test
    public void shouldNotInvokeCaptureValuesFromPatternIfNoGroupsFound() throws Exception {
        capturer.capture("hello", "world");
        verify(capturer, never()).captureValuesFromPattern(anyString(), anyListOf(String.class), anyString());
    }

    @Test
    public void shouldInvokeCaptureValuesFromPatternIfAtLeastOneGroupFound() throws Exception {
        capturer.capture("{(hello)}", "world");
        verify(capturer).captureValuesFromPattern(anyString(), anyListOf(String.class), anyString());
    }

    @Test
    public void shouldNotInvokeCaptureValuesFromPatternIfRegexDoesNotMatchValue() throws Exception {
        capturer.capture("{(hello)} Riga", "hello world");
        verify(capturer, never()).captureValuesFromPattern(anyString(), anyListOf(String.class), anyString());
    }
}
