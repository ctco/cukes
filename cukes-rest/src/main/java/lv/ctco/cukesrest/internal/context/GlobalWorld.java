package lv.ctco.cukesrest.internal.context;

import com.google.inject.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.*;
import lv.ctco.cukesrest.internal.context.capture.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

@Singleton
class GlobalWorld {

    private final Map<String, String> context = new ConcurrentHashMap<String, String>();

    public GlobalWorld() {
        /* User Specified Values */
        Properties prop = new Properties();
        URL url = GlobalWorld.class.getClassLoader().getResource("cukes.properties");
        if (url != null) {
            try {
                prop.load(url.openStream());
                loadContextFromProperties(System.getProperties());
                loadContextFromProperties(prop);
            } catch (IOException e) {
                throw new CukesInternalException(e);
            }
        }

        /* World Default Values */
        defaultProperty(prop, CukesOptions.RESOURCES_ROOT, "src/test/resources/");
        defaultProperty(prop, CukesOptions.BASE_URI, "http://localhost:80");
        defaultProperty(prop, CukesOptions.ENVIRONMENT, null);
        defaultProperty(prop, CukesOptions.AUTH_TYPE, null);
        defaultProperty(prop, CukesOptions.PASSWORD, null);
        defaultProperty(prop, CukesOptions.USERNAME, null);
        defaultProperty(prop, CukesOptions.PROXY, null);
        defaultProperty(prop, CukesOptions.RELAXED_HTTPS, null);

        defaultProperty(prop, CukesOptions.ASSERTIONS_DISABLED, false);
        defaultProperty(prop, CukesOptions.URL_ENCODING_ENABLED, false);
        defaultProperty(prop, CukesOptions.CONTEXT_INFLATING_ENABLED, true);

        defaultProperty(prop, CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS, false);

    }

    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value) {
        if (value != null) context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public String get(String key) {
        return context.get(key);
    }

    private void loadContextFromProperties(Properties prop) {
        Set<Map.Entry<Object, Object>> properties = prop.entrySet();
        for (Map.Entry<Object, Object> property : properties) {
            String key = property.getKey().toString();
            if (key.startsWith(CukesOptions.PROPERTIES_PREFIX_VAR)) {
                String value = key.split(CukesOptions.PROPERTIES_PREFIX_VAR + ".")[1];
                if (property.getValue() instanceof String) {
                    put(value, property.getValue().toString());
                }
            }
        }
    }

    private void defaultProperty(Properties prop, String key, String defaultValue) {
        String value = prop.getProperty(CukesOptions.PROPERTIES_PREFIX + key);
        if (value != null) {
            put(key, value);
        } else {
            put(key, defaultValue);
        }
    }

    private void defaultProperty(Properties prop, String key, Object defaultValue) {
        defaultProperty(prop, key, String.valueOf(defaultValue));
    }
}
