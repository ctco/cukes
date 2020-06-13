package lv.ctco.cukes.http.extension;

import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.AbstractCukesModule;

public abstract class AbstractCukesHttpModule extends AbstractCukesModule {

    protected <T extends CukesHttpPlugin> void registerHttpPlugin(Class<T> pluginClass) {
        Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
        multibinder.addBinding().to(pluginClass);
    }
}
