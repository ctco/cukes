package lv.ctco.cukes.rest;

import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.http.extension.AbstractCukesHttpModule;
import lv.ctco.cukes.rest.internal.PreprocessRestRequestBody;

@CukesInjectableModule
public class CukesRestGuiceModule extends AbstractCukesHttpModule {

    @Override
    protected void configure() {
        registerHttpPlugin(PreprocessRestRequestBody.class);
    }
}
