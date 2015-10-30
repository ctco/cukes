package lv.ctco.cukesrest.internal.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;

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
