package lv.ctco.cukescore.internal.context;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import java.util.Set;

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

    public Set<String> getKeysStartingWith(final String headerPrefix) {
        Set<String> keys = world.keys();
        return Sets.filter(keys, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.startsWith(headerPrefix);
            }
        });
    }

    public void remove(String key) {
        world.remove(key);
    }
}
