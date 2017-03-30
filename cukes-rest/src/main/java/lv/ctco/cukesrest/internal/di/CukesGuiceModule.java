package lv.ctco.cukesrest.internal.di;

import com.google.inject.*;
import com.google.inject.matcher.*;
import com.google.inject.multibindings.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.AssertionFacade;
import lv.ctco.cukesrest.internal.AssertionFacadeImpl;
import lv.ctco.cukesrest.internal.VariableFacade;
import lv.ctco.cukesrest.internal.VariableFacadeImpl;
import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.internal.logging.HttpLoggingPlugin;
import lv.ctco.cukesrest.internal.switches.*;
import org.aopalliance.intercept.*;

import java.lang.annotation.*;
import java.net.*;
import java.util.*;

import static lv.ctco.cukesrest.internal.AssertionFacade.*;
import static lv.ctco.cukesrest.internal.VariableFacade.*;

public class CukesGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bindInterceptor(new InflateContextInterceptor(), InflateContext.class);
        bindInterceptor(new CaptureContextInterceptor(), CaptureContext.class);
        bindInterceptor(new SwitchedByInterceptor(), SwitchedBy.class);

        bindAssertionFacade();
        bindVariableFacade();
        bindPlugins();
    }

    @SuppressWarnings("unchecked")
    private void bindAssertionFacade() {
        bindAlternative(ASSERTION_FACADE, AssertionFacade.class, AssertionFacadeImpl.class);
    }

    @SuppressWarnings("unchecked")
    private void bindVariableFacade() {
        bindAlternative(VARIABLE_FACADE, VariableFacade.class, VariableFacadeImpl.class);
    }

    private <T, E extends T> void bindAlternative(String type, Class<T> clazz, Class<E> defaultClass) {
        String alternativeType = System.getProperty(type);
        Class<? extends T> targetClass;
        if (alternativeType == null || alternativeType.isEmpty()) {
            targetClass = defaultClass;
        } else {
            try {
                targetClass = (Class<T>) Class.forName(alternativeType);
            } catch (ClassNotFoundException e) {
                throw new CukesRuntimeException("Invalid " + type + " value", e);
            } catch (ClassCastException e) {
                throw new CukesRuntimeException("Invalid " + type + " value", e);
            }
        }
        bind(clazz).to(targetClass);
    }

    private void bindInterceptor(MethodInterceptor interceptor, Class<? extends Annotation> annotationType) {
        requestInjection(interceptor);
        bindInterceptor(Matchers.annotatedWith(annotationType), Matchers.any(), interceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(annotationType), interceptor);
    }

    @SuppressWarnings("unchecked")
    private void bindPlugins() {
        try {
            Multibinder<CukesRestPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesRestPlugin.class);

            // add our own plugins
            multibinder.addBinding().to(HttpLoggingPlugin.class);

            // add user configured plugins
            ClassLoader classLoader = CukesGuiceModule.class.getClassLoader();
            Properties prop = new Properties();
            URL url = createCukesPropertyFileUrl(classLoader);
            if (url == null) return;
            prop.load(url.openStream());
            String pluginsArr = prop.getProperty(CukesOptions.PROPERTIES_PREFIX + CukesOptions.PLUGINS);
            if (pluginsArr == null) return;
            String[] pluginClasses = pluginsArr.split(CukesOptions.DELIMITER);
            for (String pluginClass : pluginClasses) {
                Class<? extends CukesRestPlugin> aClass = (Class<? extends CukesRestPlugin>) classLoader.loadClass(pluginClass);
                multibinder.addBinding().to(aClass);
            }
        } catch (Exception e) {
            throw new CukesRuntimeException("Binding of CukesRest plugins failed");
        }
    }

    /**
     * @see GlobalWorld#createCukesPropertyFileUrl(ClassLoader)
     */
    private URL createCukesPropertyFileUrl(final ClassLoader classLoader) {
        String cukesProfile = System.getProperty("cukes.profile");
        String propertiesFileName = cukesProfile == null || cukesProfile.isEmpty()
            ? "cukes.properties"
            : "cukes-" + cukesProfile + ".properties";
        return classLoader.getResource(propertiesFileName);
    }
}
