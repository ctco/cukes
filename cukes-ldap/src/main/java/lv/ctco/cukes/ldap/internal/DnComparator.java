package lv.ctco.cukes.ldap.internal;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

public class DnComparator implements Comparator<String> {
    private boolean reverse;

    public DnComparator(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public int compare(String o1, String o2) {
        return (reverse ? -1 : 1) * StringUtils.reverse(o1).compareTo(StringUtils.reverse(o2));
    }
}
