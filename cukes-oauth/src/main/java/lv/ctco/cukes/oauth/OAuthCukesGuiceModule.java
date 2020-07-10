package lv.ctco.cukes.oauth;

import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.http.extension.AbstractCukesHttpModule;

@CukesInjectableModule
public class OAuthCukesGuiceModule extends AbstractCukesHttpModule {

    @Override
    protected void configure() {
        registerHttpPlugin(OAuthCukesHttpPlugin.class);
    }
}
