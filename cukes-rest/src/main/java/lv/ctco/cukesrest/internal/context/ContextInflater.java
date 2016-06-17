package lv.ctco.cukesrest.internal.context;

import com.google.common.base.Optional;
import com.google.inject.*;
import lv.ctco.cukesrest.*;

import java.util.*;

public class ContextInflater extends BaseContextHandler {

    @Inject
    GlobalWorldFacade world;

    public String inflate(String input) {
        Set<String> groups = new HashSet<String>(extractGroups(input));
        boolean inflatingEnabled = world.getBoolean(CukesOptions.CONTEXT_INFLATING_ENABLED, true);
        if (inflatingEnabled) {
            return inflateGroups(input, groups);
        }
        return inflateGroupsWithPlaceholders(input, groups);
    }

    String inflateGroups(String input, Set<String> groups) {
        String result = input;
        for (String key : groups) {
            Optional<String> $value = world.get(key);
            if ($value.isPresent()) {
                result = result.replaceAll("\\{\\(" + key + "\\)\\}", $value.get());
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
