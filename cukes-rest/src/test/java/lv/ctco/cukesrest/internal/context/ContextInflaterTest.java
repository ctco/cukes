package lv.ctco.cukesrest.internal.context;

import com.google.common.collect.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContextInflaterTest {

    @InjectMocks
    ContextInflater inflater;

    @Mock
    GlobalWorldFacade world;

    @Test
    public void testInflateGroups() throws Exception {
        doReturn("foo").when(world).get("foo");
        String value = inflater.inflateGroups("{(foo)} bar", Sets.newHashSet("foo"));
        assertThat(value, equalTo("foo bar"));
    }

    @Test
    public void testInflateGroups_emptyWorld() throws Exception {
        String value = inflater.inflateGroups("{(foo)} bar", Sets.newHashSet("foo"));
        assertThat(value, equalTo("{(foo)} bar"));
    }

    @Test
    public void testInflateGroups_multipleEmpty() throws Exception {
        String value = inflater.inflateGroups("{(foo)} {(bar)}", Sets.newHashSet("foo", "bar"));
        assertThat(value, equalTo("{(foo)} {(bar)}"));
    }

    @Test
    public void testInflateGroups_halfEmpty() throws Exception {
        doReturn("foo").when(world).get("foo");
        String value = inflater.inflateGroups("{(foo)} {(bar)}", Sets.newHashSet("foo", "bar"));
        assertThat(value, equalTo("foo {(bar)}"));
    }

    @Test
    public void testInflateGroups_withPlainText() throws Exception {
        doReturn("foo").when(world).get("foo");
        String value = inflater.inflateGroups("my {(foo)} is very {(bar)} !", Sets.newHashSet("foo", "bar"));
        assertThat(value, equalTo("my foo is very {(bar)} !"));
    }

    @Test
    public void testInflateGroups_multipleExist() throws Exception {
        doReturn("foo").when(world).get("foo");
        doReturn("bar").when(world).get("bar");
        String value = inflater.inflateGroups("{(foo)} {(bar)}", Sets.newHashSet("foo", "bar"));
        assertThat(value, equalTo("foo bar"));
    }

    @Test
    public void testInflateGroups_multipleSameExist() throws Exception {
        doReturn("foo").when(world).get("foo");
        String value = inflater.inflateGroups("{(foo)} {(foo)}", Sets.newHashSet("foo"));
        assertThat(value, equalTo("foo foo"));
    }

    @Test
    public void testInflateGroups_multipleSameEmpty() throws Exception {
        String value = inflater.inflateGroups("{(foo)} {(foo)}", Sets.newHashSet("foo"));
        assertThat(value, equalTo("{(foo)} {(foo)}"));
    }
}
