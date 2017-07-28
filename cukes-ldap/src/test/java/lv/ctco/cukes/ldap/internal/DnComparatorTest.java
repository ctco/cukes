package lv.ctco.cukes.ldap.internal;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DnComparatorTest {

    DnComparator comparator = new DnComparator(false);

    @Test
    public void compare_sameTree() throws Exception {
        assertThat(comparator.compare("cn=root", "cn=b,cn=root"), is(more()));
        assertThat(comparator.compare("cn=a,cn=root", "cn=root"), is(less()));
        assertThat(comparator.compare("cn=a,cn=root", "cn=a,cn=root"), is(same()));
    }

    @Test
    public void compare_differentTrees() throws Exception {
        assertThat(comparator.compare("cn=a,cn=root", "cn=b,cn=root"), is(more()));
        assertThat(comparator.compare("cn=b,cn=root", "cn=a,cn=root"), is(less()));
    }

    @Test
    public void sort() throws Exception {
        List<String> dns = new ArrayList<>(Arrays.asList(
            "cn=root",
            "cn=a,cn=root",
            "cn=b,cn=root",
            "cn=c,cn=a,cn=root"
        ));
        Collections.sort(dns, comparator);
        assertThat(dns.get(0), is("cn=root"));
        assertThat(dns.get(1), is("cn=a,cn=root"));
        assertThat(dns.get(2), is("cn=c,cn=a,cn=root"));
        assertThat(dns.get(3), is("cn=b,cn=root"));
    }

    static Matcher<Integer> more() {
        return Matchers.lessThan(0);
    }

    static Matcher<Integer> less() {
        return Matchers.greaterThan(0);
    }

    static Matcher<Integer> same() {
        return Matchers.is(0);
    }
}
