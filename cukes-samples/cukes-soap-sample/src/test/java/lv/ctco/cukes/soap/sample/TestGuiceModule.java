package lv.ctco.cukes.soap.sample;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.AbstractCukesModule;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.http.extension.AbstractCukesHttpModule;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.rest.internal.PreprocessRestRequestBody;

@CukesInjectableModule
public class TestGuiceModule extends AbstractCukesHttpModule {

    @Override
    protected void configure() {
        registerHttpPlugin(TestPlugin.class);
    }
}
