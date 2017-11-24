package lv.ctco.cukes.core.extension;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public abstract class AbstractCukesModule extends AbstractModule {

    protected <T extends CukesPlugin> void registerPlugin(Class<T> pluginClass) {
        Multibinder<CukesPlugin> baseMultibinder = Multibinder.newSetBinder(binder(), CukesPlugin.class);
        baseMultibinder.addBinding().to(pluginClass);
    }

}
