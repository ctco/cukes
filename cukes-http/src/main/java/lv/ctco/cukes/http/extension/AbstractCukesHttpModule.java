package lv.ctco.cukes.http.extension;

import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.AbstractCukesModule;
import lv.ctco.cukes.core.extension.CukesPlugin;

public abstract class AbstractCukesHttpModule extends AbstractCukesModule {

    protected <T extends CukesHttpPlugin> void registerHttpPlugin(Class<T> pluginClass) {
        Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
        multibinder.addBinding().to(pluginClass);
        registerPlugin(pluginClass);
    }
}
