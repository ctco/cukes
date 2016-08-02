package lv.ctco.cukesrest.internal.context;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import java.beans.PropertyChangeListener;

public class GlobalWorldFacade {

    @Inject
    GlobalWorld world;

    @CaptureContext
    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value, ContextScope scope) {
        world.put(key, value, scope);
    }

    public Optional<String> get(String key) {
        return world.get(key);
    }

    public String get(String key, String defaultValue) {
        Optional<String> value = world.get(key);
        return value.isPresent() ? value.get() : defaultValue;
    }

    public void addPropertyChangeListener(String propertyKey, PropertyChangeListener listener) {
        world.addPropertyChangeListener(propertyKey, listener);
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

    public void clearScenarioScope() {
        world.clear(ContextScope.REQUEST);
        world.clear(ContextScope.SCENARIO);
    }

    public void clearRequestScope() {
        world.clear(ContextScope.REQUEST);
    }
}
