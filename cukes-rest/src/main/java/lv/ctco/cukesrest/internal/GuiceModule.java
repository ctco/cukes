package lv.ctco.cukesrest.internal;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukesrest.CukesOptions;
import lv.ctco.cukesrest.CukesRestPlugin;
import lv.ctco.cukesrest.CukesRuntimeException;
import lv.ctco.cukesrest.internal.context.CaptureContext;
import lv.ctco.cukesrest.internal.context.CaptureContextInterceptor;
import lv.ctco.cukesrest.internal.context.InflateContext;
import lv.ctco.cukesrest.internal.context.InflateContextInterceptor;
import lv.ctco.cukesrest.internal.switches.SwitchedBy;
import lv.ctco.cukesrest.internal.switches.SwitchedByInterceptor;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Properties;

import static lv.ctco.cukesrest.internal.AssertionFacade.ASSERTION_FACADE;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(new InflateContextInterceptor(), InflateContext.class);
        bindInterceptor(new CaptureContextInterceptor(), CaptureContext.class);
        bindInterceptor(new SwitchedByInterceptor(), SwitchedBy.class);

        bindAssertionFacade();
        bindPlugins();
    }

    @SuppressWarnings("unchecked")
    private void bindAssertionFacade() {
        String facadeImplType = System.getProperty(ASSERTION_FACADE);
        Class<? extends AssertionFacade> assertionFacadeClass;
        if (facadeImplType == null || facadeImplType.isEmpty()) {
            assertionFacadeClass = AssertionFacadeImpl.class;
        } else {
            try {
                assertionFacadeClass = (Class<AssertionFacade>) Class.forName(facadeImplType);
            } catch (ClassNotFoundException e) {
                throw new CukesRuntimeException("Invalid " + ASSERTION_FACADE + " value", e);
            } catch (ClassCastException e) {
                throw new CukesRuntimeException("Invalid " + ASSERTION_FACADE + " value", e);
            }
        }
        bind(AssertionFacade.class).to(assertionFacadeClass);
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
            ClassLoader classLoader = GuiceModule.class.getClassLoader();
            Properties prop = new Properties();
            URL url = classLoader.getResource("cukes.properties");
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
}
