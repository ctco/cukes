package lv.ctco.cukes.core.internal.context;

import com.google.inject.Inject;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GlobalWorldFacade {

    @Inject
    GlobalWorld world;

    @CaptureContext
    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value) {
        world.put(key, value);
    }

    public String getOrThrow(String key) {
        return world.get(key).orElseGet(() -> {
            throw new CukesMissingPropertyException(key);
        });
    }

    public Optional<String> get(String key) {
        return world.get(key);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, Boolean.toString(defaultValue)));
    }

    public String get(String key, String defaultValue) {
        Optional<String> value = world.get(key);
        return value.orElse(defaultValue);
    }

    public void reconstruct() {
        world.reconstruct();
    }

    public Set<String> getKeysStartingWith(final String headerPrefix) {
        Set<String> keys = world.keys();
        return keys.stream()
            .filter(Objects::nonNull)
            .filter(s -> s.startsWith(headerPrefix))
            .collect(Collectors.toSet());
    }

    public void remove(String key) {
        world.remove(key);
    }

    public Set<String> keys() {
        return world.keys();
    }
}
