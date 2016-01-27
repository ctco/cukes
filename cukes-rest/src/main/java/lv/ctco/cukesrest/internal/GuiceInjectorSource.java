package lv.ctco.cukesrest.internal;

import com.google.inject.*;
import cucumber.api.guice.*;
import cucumber.runtime.java.guice.*;

public class GuiceInjectorSource implements InjectorSource {

    private static Injector injector;

    @Override
    public Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, new GuiceModule());
        }
        return injector;
    }
}
