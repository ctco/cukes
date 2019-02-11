package lv.ctco.cukes.core.extension;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.CukesRuntimeException;
import org.apache.commons.lang3.ArrayUtils;

import java.net.URL;
import java.util.Properties;

import static lv.ctco.cukes.core.internal.helpers.Files.createCukesPropertyFileUrl;

public abstract class AbstractCukesModule extends AbstractModule {

    protected <T extends CukesPlugin> void registerPlugin(Class<T> pluginClass) {
        Multibinder<CukesPlugin> baseMultibinder = Multibinder.newSetBinder(binder(), CukesPlugin.class);
        baseMultibinder.addBinding().to(pluginClass);
    }

    @SuppressWarnings("unchecked")
    protected void bindPlugins(Class clazz) {
        try {
            Multibinder multibinder = Multibinder.newSetBinder(binder(), clazz);

            // add user configured plugins
            ClassLoader classLoader = AbstractCukesModule.class.getClassLoader();

            Properties properties = new Properties();
            URL url = createCukesPropertyFileUrl(classLoader);
            if (url == null) return;
            properties.load(url.openStream());

            String plugins = properties.getProperty(CukesOptions.PROPERTIES_PREFIX + CukesOptions.PLUGINS);
            if (plugins == null) return;

            String[] pluginClasses = plugins.split(CukesOptions.DELIMITER);
            for (String pluginClass : pluginClasses) {
                Class aClass = classLoader.loadClass(pluginClass);
                if (ArrayUtils.contains(aClass.getInterfaces(), clazz)) {
                    multibinder.addBinding().to(aClass);
                }
            }
        } catch (Exception e) {
            throw new CukesRuntimeException("Binding of Cukes plugins failed");
        }
    }
}
