package lv.ctco.cukes.core.internal.context;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static lv.ctco.cukes.core.CukesOptions.BASE_URI;
import static lv.ctco.cukes.core.CukesOptions.PROPERTIES_PREFIX;
import static lv.ctco.cukes.core.CukesOptions.RESOURCES_ROOT;
import static lv.ctco.cukes.core.internal.helpers.Files.createCukesPropertyFileUrl;

@Singleton
public class GlobalWorld {
    private Map<String, String> context;

    @Inject
    public void reconstruct() {
        /* User Specified Values */
        context = new ConcurrentHashMap<>();
        Properties prop = new Properties();
        URL url = createCukesPropertyFileUrl(GlobalWorld.class.getClassLoader());
        if (url != null) {
            try {
                prop.load(url.openStream());
                loadContextFromProperties(prop);
                loadContextFromProperties(System.getProperties());
            } catch (IOException e) {
                throw new CukesRuntimeException(e);
            }
        }

        /* World Default Values */
        defaultProperty(RESOURCES_ROOT, "src/test/resources/");
        defaultProperty(BASE_URI, "http://localhost:80");
    }

    public void put(@CaptureContext.Pattern String key, @CaptureContext.Value String value) {
        context.put(key, value);
    }

    public Optional<String> get(String key) {
        return Optional.fromNullable(context.get(key));
    }

    private void loadContextFromProperties(Properties prop) {
        Set<Map.Entry<Object, Object>> properties = prop.entrySet();
        for (Map.Entry<Object, Object> property : properties) {
            String key = property.getKey().toString();
            if (key.startsWith(PROPERTIES_PREFIX)) {
                String value = key.split(PROPERTIES_PREFIX)[1];
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

    public Set<String> keys() {
        return context.keySet();
    }

    public void remove(String key) {
        context.remove(key);
    }
}
