package lv.ctco.cukesrest;

import com.google.inject.Injector;
import lv.ctco.cukesrest.internal.guice.GuiceInjectorSource;

public class IntegrationTestBase {

    private Injector injector = new GuiceInjectorSource().getInjector();

    public Injector getInjector() {
        return injector;
    }
}
