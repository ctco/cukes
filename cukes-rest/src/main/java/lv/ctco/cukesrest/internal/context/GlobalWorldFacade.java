package lv.ctco.cukesrest.internal.context;

import com.google.inject.Inject;
import lv.ctco.cukesrest.internal.context.capture.CaptureContext;

public class GlobalWorldFacade {

    @Inject
    GlobalWorld world;

    @CaptureContext
    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value) {
        world.put(key, value);
    }

    public String get(String key) {
        return world.get(key);
    }

    public String get(String key, String defaultValue) {
        String value = world.get(key);
        return value == null ? defaultValue : value;
    }

    public Boolean getBoolean(String key) {
        return Boolean.valueOf(world.get(key));
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        Boolean value = Boolean.valueOf(world.get(key));
        return value == null ? defaultValue : value;
    }

    public Integer getInteger(String key, Integer defaultValue) {
        Integer value = Integer.valueOf(world.get(key));
        return value == null ? defaultValue : value;
    }
}
