package lv.ctco.cukesrest.internal.context;

import com.google.inject.Singleton;
import lv.ctco.cukesrest.internal.CukesInternalException;
import lv.ctco.cukesrest.internal.context.capture.CaptureContext;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static lv.ctco.cukesrest.CukesOptions.*;

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
                loadContextFromProperties(prop);
                loadContextFromProperties(System.getProperties());
            } catch (IOException e) {
                throw new CukesInternalException(e);
            }
        }

        /* World Default Values */
        defaultProperty(RESOURCES_ROOT, "src/test/resources/");
        defaultProperty(BASE_URI, "http://localhost:80");

        defaultProperty(ASSERTIONS_DISABLED, false);
        defaultProperty(URL_ENCODING_ENABLED, false);
        defaultProperty(CONTEXT_INFLATING_ENABLED, true);

        defaultProperty(LOADRUNNER_FILTER_BLOCKS_REQUESTS, false);

    }

    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public String get(String key) {
        return context.get(key);
    }

    private void loadContextFromProperties(Properties prop) {
        Set<Map.Entry<Object, Object>> properties = prop.entrySet();
        for (Map.Entry<Object, Object> property : properties) {
            String key = property.getKey().toString();
            if (key.startsWith(PROPERTIES_PREFIX)) {
                String value = key.split(PROPERTIES_PREFIX + ".")[1];
                if (property.getValue() instanceof String) {
                    put(value, property.getValue().toString());
                }
            }
        }
    }

    private void defaultProperty(String key, Object defaultValue) {
        String value = context.get(key);
        if (value == null) {
            put(key, String.valueOf(defaultValue));
        }
    }
}
