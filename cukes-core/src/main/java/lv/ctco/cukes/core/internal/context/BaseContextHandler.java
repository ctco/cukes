package lv.ctco.cukes.core.internal.context;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseContextHandler {

    public static final String GROUP_PATTERN = "\\{\\(([\\w\\.-]+)\\)\\}";

    protected List<String> extractGroups(String pattern) {
        List<String> allMatches = new ArrayList<String>();
        if (pattern == null) {
            return allMatches;
        }
        Matcher m = Pattern.compile(GROUP_PATTERN).matcher(pattern);
        while (m.find()) {
            allMatches.add(m.group(1));
        }

        return allMatches;
    }
}
