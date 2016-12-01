package lv.ctco.cukesrest.di;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import cucumber.api.guice.CucumberModules;
import cucumber.api.java.ObjectFactory;
import cucumber.runtime.java.guice.ScenarioScope;
import lv.ctco.cukesrest.internal.di.CukesGuiceModule;

import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class SingletonObjectFactory implements ObjectFactory {

    private static final Set<Module> MODULES = Sets.newConcurrentHashSet();

    static {
        MODULES.add(CucumberModules.SCENARIO);
        MODULES.add(new CukesGuiceModule());
    }

    private static Injector injector = null;

    /**
     * Internal use only.
     * <br><br>
     * Use {@link SingletonObjectFactory#instance()} instead.
     */
    public SingletonObjectFactory() { // required
    }

    @Override
    public void start() {
        lazyInitInjector();
        injector.getInstance(ScenarioScope.class).enterScope();
    }

    @Override
    public void stop() {
        lazyInitInjector();
        injector.getInstance(ScenarioScope.class).exitScope();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> aClass) {
        lazyInitInjector();
        return injector.getInstance(aClass);
    }

    public void addModule(Module module) {
        checkState(injector == null, "Cannot add modules after the factory has been used!");
        MODULES.add(module);
    }

    private static void lazyInitInjector() {
        if (injector == null) {
            injector = Guice.createInjector(Stage.PRODUCTION, MODULES);
        }
    }

    public static SingletonObjectFactory instance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        static final SingletonObjectFactory INSTANCE = new SingletonObjectFactory();
    }
}
