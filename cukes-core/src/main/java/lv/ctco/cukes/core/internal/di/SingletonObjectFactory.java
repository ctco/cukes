package lv.ctco.cukes.core.internal.di;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import cucumber.api.guice.CucumberModules;
import cucumber.api.java.ObjectFactory;
import cucumber.runtime.java.guice.ScenarioScope;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
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
     *
     * @deprecated
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

    private void lazyInitInjector() {
        if (injector == null) {
            addExternalModules();
            injector = Guice.createInjector(Stage.PRODUCTION, MODULES);
        }
    }

    private void addExternalModules() {
        Reflections reflections = new Reflections("lv.ctco.cukes");
        for (Class targetClass : reflections.getTypesAnnotatedWith(CukesInjectableModule.class)) {
            try {
                Constructor<Module> constructor = targetClass.getConstructor();
                Module module = constructor.newInstance();
                addModule(module);
            } catch (Exception e) {
                throw new CukesRuntimeException("Unable to add External Module to Guice");
            }
        }
    }

    public static SingletonObjectFactory instance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        static final SingletonObjectFactory INSTANCE = new SingletonObjectFactory();
    }
}
