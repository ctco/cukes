package lv.ctco.cukesrest.internal.context;

import com.google.common.base.*;
import com.google.inject.*;

public class GlobalWorldFacade {

    @Inject
    GlobalWorld world;

    @CaptureContext
    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value) {
        world.put(key, value);
    }

    public Optional<String> get(String key) {
        return world.get(key);
    }

    public String get(String key, String defaultValue) {
        Optional<String> value = world.get(key);
        return value.isPresent() ? value.get() : defaultValue;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.valueOf(get(key, Boolean.toString(defaultValue)));
    }

    public void reconstruct() {
        world.reconstruct();
    }
}
