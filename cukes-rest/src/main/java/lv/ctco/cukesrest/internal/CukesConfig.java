package lv.ctco.cukesrest.internal;

import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.internal.guice.*;

@Deprecated
public class CukesConfig {

    private final GlobalWorldFacade globalWorldFacade;

    public CukesConfig() {
        globalWorldFacade = new GuiceInjectorSource().getInjector().getInstance(GlobalWorldFacade.class);
    }

    public CukesConfig setVar(String key, String value) {
        System.setProperty(key, value);
        return this;
    }

    public CukesConfig setVar(String key, Object value) {
        return setVar(key, String.valueOf(value));
    }

    public String getVar(String key) {
        return globalWorldFacade.get(key);
    }
}
