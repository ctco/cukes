package lv.ctco.cukes.oauth;

import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.http.extension.AbstractCukesHttpModule;
import lv.ctco.cukes.oauth.OAuthCukesHttpPlugin;

@CukesInjectableModule
public class OAuthCukesGuiceModule extends AbstractCukesHttpModule {

    @Override
    protected void configure() {
        registerHttpPlugin(OAuthCukesHttpPlugin.class);
    }
}
