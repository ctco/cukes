package lv.ctco.cukesrest.internal;

import com.google.inject.*;

public class IntegrationTestBase {

    private Injector injector = new GuiceInjectorSource().getInjector();

    public Injector getInjector() {
        return injector;
    }
}
