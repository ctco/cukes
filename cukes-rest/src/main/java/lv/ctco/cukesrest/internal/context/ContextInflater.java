package lv.ctco.cukesrest.internal.context;

import com.google.inject.*;
import lv.ctco.cukesrest.*;

import java.util.*;

public class ContextInflater extends BaseContextHandler {

    @Inject
    GlobalWorldFacade world;

    public String inflate(String input) {
        Set<String> groups = new HashSet<String>(extractGroups(input));
        if (world.getBoolean(CukesOptions.CONTEXT_INFLATING_ENABLED)) {
            return inflateGroups(input, groups);
        }
        return inflateGroupsWithPlaceholders(input, groups);
    }

    String inflateGroups(String input, Set<String> groups) {
        String result = input;
        for (String key : groups) {
            String value = world.get(key);
            if (value != null) {
                result = result.replaceAll("\\{\\(" + key + "\\)\\}", value);
            }
        }
        return result;
    }

    String inflateGroupsWithPlaceholders(String input, Set<String> groups) {
        String result = input;
        for (String key : groups) {
            result = result.replaceAll("\\{\\(" + key + "\\)\\}", "(" + key + ")");
        }
        return result;
    }
}
