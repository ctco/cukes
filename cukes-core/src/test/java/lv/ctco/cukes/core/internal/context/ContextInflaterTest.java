package lv.ctco.cukes.core.internal.context;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ContextInflaterTest {

    @InjectMocks
    ContextInflater inflater;

    @Mock
    GlobalWorldFacade world;

    @Before
    public void setUp() {
        doReturn(Optional.absent()).when(world).get(anyString());
    }

    @Test
    public void testInflateGroups() throws Exception {
        doReturn(Optional.of("foo")).when(world).get("foo");
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
        doReturn(Optional.of("foo")).when(world).get("foo");
        String value = inflater.inflateGroups("{(foo)} {(bar)}", Sets.newHashSet("foo", "bar"));
        assertThat(value, equalTo("foo {(bar)}"));
    }

    @Test
    public void testInflateGroups_withPlainText() throws Exception {
        doReturn(Optional.of("foo")).when(world).get("foo");
        String value = inflater.inflateGroups("my {(foo)} is very {(bar)} !", Sets.newHashSet("foo", "bar"));
        assertThat(value, equalTo("my foo is very {(bar)} !"));
    }

    @Test
    public void testInflateGroups_multipleExist() throws Exception {
        doReturn(Optional.of("foo")).when(world).get("foo");
        doReturn(Optional.of("bar")).when(world).get("bar");
        String value = inflater.inflateGroups("{(foo)} {(bar)}", Sets.newHashSet("foo", "bar"));
        assertThat(value, equalTo("foo bar"));
    }

    @Test
    public void testInflateGroups_multipleSameExist() throws Exception {
        doReturn(Optional.of("foo")).when(world).get("foo");
        String value = inflater.inflateGroups("{(foo)} {(foo)}", Sets.newHashSet("foo"));
        assertThat(value, equalTo("foo foo"));
    }

    @Test
    public void testInflateGroups_multipleSameEmpty() throws Exception {
        String value = inflater.inflateGroups("{(foo)} {(foo)}", Sets.newHashSet("foo"));
        assertThat(value, equalTo("{(foo)} {(foo)}"));
    }
}
