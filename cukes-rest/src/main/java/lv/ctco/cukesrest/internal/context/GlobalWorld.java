package lv.ctco.cukesrest.internal.context;

import com.google.inject.*;
import com.google.common.base.Optional;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.di.CukesGuiceModule;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import static lv.ctco.cukesrest.CukesOptions.*;

@Singleton
class GlobalWorld {
    private Map<String, String> context;

    @Inject
    public void reconstruct() {
        /* User Specified Values */
        context = new ConcurrentHashMap<String, String>();
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

    /**
     * @see CukesGuiceModule#createCukesPropertyFileUrl(ClassLoader)
     */
    private URL createCukesPropertyFileUrl(final ClassLoader classLoader) {
        String cukesProfile = System.getProperty("cukes.profile");
        String propertiesFileName = cukesProfile == null || cukesProfile.isEmpty()
            ? "cukes.properties"
            : "cukes-" + cukesProfile + ".properties";
        return classLoader.getResource(propertiesFileName);
    }
}
