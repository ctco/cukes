package lv.ctco.cukes.rest;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.RandomGeneratorFacadeImpl;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.facade.VariableFacadeImpl;
import lv.ctco.cukes.core.internal.di.CukesGuiceModule;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.http.logging.HttpLoggingPlugin;
import lv.ctco.cukes.rest.facade.RestAssertionFacade;
import lv.ctco.cukes.rest.facade.RestAssertionFacadeImpl;

import java.net.URL;
import java.util.Properties;

import static lv.ctco.cukes.core.CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS;
import static lv.ctco.cukes.core.CukesOptions.PROPERTIES_PREFIX;
import static lv.ctco.cukes.core.internal.helpers.Files.createCukesPropertyFileUrl;

@CukesInjectableModule
public class CukesRestGuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        boolean isLoadRunnedEnabled = Boolean.parseBoolean(System.getProperty(PROPERTIES_PREFIX + LOADRUNNER_FILTER_BLOCKS_REQUESTS));
        if (!isLoadRunnedEnabled) {
            bind(RestAssertionFacade.class).to(RestAssertionFacadeImpl.class);
            bind(VariableFacade.class).to(VariableFacadeImpl.class);
            bind(RandomGeneratorFacade.class).to(RandomGeneratorFacadeImpl.class);
        }

        bindPlugins();
    }

    private void bindPlugins() {
        try {
            Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
            multibinder.addBinding().to(HttpLoggingPlugin.class);

            // add user configured plugins
            ClassLoader classLoader = CukesGuiceModule.class.getClassLoader();

            Properties properties = new Properties();
            URL url = createCukesPropertyFileUrl(classLoader);
            if (url == null) return;
            properties.load(url.openStream());

            String plugins = properties.getProperty(CukesOptions.PROPERTIES_PREFIX + CukesOptions.PLUGINS);
            if (plugins == null) return;

            String[] pluginClasses = plugins.split(CukesOptions.DELIMITER);
            for (String pluginClass : pluginClasses) {
                Class<? extends CukesPlugin> aClass = (Class<? extends CukesPlugin>) classLoader.loadClass(pluginClass);
                if (CukesHttpPlugin.class.isAssignableFrom(aClass))
                    multibinder.addBinding().to((Class<? extends CukesHttpPlugin>) aClass);
            }
        } catch (Exception e) {
            throw new CukesRuntimeException("Binding of Cukes http plugins failed");
        }
    }
}
