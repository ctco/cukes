package lv.ctco.cukesrest.internal;

import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import lv.ctco.cukesrest.internal.guice.GuiceInjectorSource;

public class CukesConfig {

    private final GlobalWorldFacade globalWorldFacade;

    public CukesConfig() {
        globalWorldFacade = new GuiceInjectorSource().getInjector().getInstance(GlobalWorldFacade.class);
    }

    public CukesConfig setVar(String key, String value) {
        globalWorldFacade.put(key, value);
        return this;
    }

    public CukesConfig setVar(String key, Object value) {
        return setVar(key, String.valueOf(value));
    }

    public <T> T getVar(String key) {
        return (T) globalWorldFacade.get(key);
    }
}
