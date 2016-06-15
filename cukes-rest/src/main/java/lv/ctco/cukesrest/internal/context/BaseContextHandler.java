package lv.ctco.cukesrest.internal.context;

import java.util.*;
import java.util.regex.*;

public abstract class BaseContextHandler {

    public static final String GROUP_PATTERN = "\\{\\((\\w+)\\)\\}";

    protected List<String> extractGroups(String pattern) {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(GROUP_PATTERN).matcher(pattern);
        while (m.find()) {
            allMatches.add(m.group(1));
        }
        return allMatches;
    }
}
