package lv.ctco.cukesrest.internal;

import com.google.inject.*;
import com.google.inject.matcher.*;
import com.google.inject.multibindings.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.internal.switches.*;
import org.aopalliance.intercept.*;
import org.reflections.*;

import java.lang.annotation.*;
import java.util.*;

import static lv.ctco.cukesrest.internal.AssertionFacade.*;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(new InflateContextInterceptor(), InflateContext.class);
        bindInterceptor(new CaptureContextInterceptor(), CaptureContext.class);
        bindInterceptor(new SwitchedByInterceptor(), SwitchedBy.class);

        bindAssertionFacade();
        bindPlugins();
    }

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

    private void bindPlugins() {
        try {
            Reflections reflections = new Reflections("");
            Set<Class<? extends CukesRestPlugin>> subTypes = reflections.getSubTypesOf(CukesRestPlugin.class);
            Multibinder<CukesRestPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesRestPlugin.class);
            for (Class<? extends CukesRestPlugin> subType : subTypes) {
                multibinder.addBinding().to(subType);
            }
        } catch (Exception e) {
            throw new CukesRuntimeException("Binding of CukesRest plugins failed");
        }
    }
}
