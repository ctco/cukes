package lv.ctco.cukes.soap.sample;

import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.http.extension.AbstractCukesHttpModule;

@CukesInjectableModule
public class TestGuiceModule extends AbstractCukesHttpModule {

    @Override
    protected void configure() {
        registerHttpPlugin(TestPlugin.class);
    }
}
