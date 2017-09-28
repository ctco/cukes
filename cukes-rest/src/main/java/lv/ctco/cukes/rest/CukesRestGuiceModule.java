package lv.ctco.cukes.rest;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.rest.internal.PreprocessRestRequestBody;

@CukesInjectableModule
public class CukesRestGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
        multibinder.addBinding().to(PreprocessRestRequestBody.class);
    }
}
