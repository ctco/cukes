package lv.ctco.cukesrest.internal.context;

import groovy.util.ObservableMap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class WorldContext {
    private PropertyChangeSupport pcs;

    Map<ContextScope, Map<String, String>> contexts = new LinkedHashMap<ContextScope, Map<String, String>>();

    @SuppressWarnings("unchecked")
    public WorldContext() {
        pcs = new PropertyChangeSupport(this);
        contexts.put(ContextScope.REQUEST, new ObservableMap());
        contexts.put(ContextScope.SCENARIO, new ObservableMap());
        contexts.put(ContextScope.GLOBAL, new ObservableMap());
    }

    public void clear(ContextScope scope) {
        final Map<String, String> context = contexts.get(scope);
        for (String key :  new HashSet<String>(context.keySet())) {
            final String oldValue = context.remove(key);
            final String newValue = get(key);
            firePropertyUpdatedEvent(key, oldValue, newValue);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void put(String key, String value, ContextScope scope) {
        String oldValue = get(key);
        contexts.get(scope).put(key, value);
        firePropertyUpdatedEvent(key, oldValue, value);
    }

    public String get(String key) {
        for (Map<String, String> context : contexts.values()) {
            if (context.containsKey(key)) {
                return context.get(key);
            }
        }
        return null;
    }

    public Set<String> keySet() {
        Set<String> keys = new HashSet<String>();
        for (Map<String, String> context : contexts.values()) {
            keys.addAll(context.keySet());
        }
        return keys;
    }


    protected void firePropertyUpdatedEvent(Object key, Object oldValue, Object newValue) {
        pcs.firePropertyChange(new ObservableMap.PropertyUpdatedEvent(this, String.valueOf(key), oldValue, newValue));
    }
}
