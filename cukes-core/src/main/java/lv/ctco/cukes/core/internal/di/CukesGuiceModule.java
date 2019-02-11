package lv.ctco.cukes.core.internal.di;

import com.google.inject.matcher.Matchers;
import lv.ctco.cukes.core.extension.AbstractCukesModule;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.core.internal.context.CaptureContext;
import lv.ctco.cukes.core.internal.context.CaptureContextInterceptor;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.context.InflateContextInterceptor;
import lv.ctco.cukes.core.internal.switches.SwitchedBy;
import lv.ctco.cukes.core.internal.switches.SwitchedByInterceptor;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;

public class CukesGuiceModule extends AbstractCukesModule {

    @Override
    protected void configure() {
        bindInterceptor(new InflateContextInterceptor(), InflateContext.class);
        bindInterceptor(new CaptureContextInterceptor(), CaptureContext.class);
        bindInterceptor(new SwitchedByInterceptor(), SwitchedBy.class);

        bindPlugins(CukesPlugin.class);
    }

    private void bindInterceptor(MethodInterceptor interceptor, Class<? extends Annotation> annotationType) {
        requestInjection(interceptor);
        bindInterceptor(Matchers.annotatedWith(annotationType), Matchers.any(), interceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(annotationType), interceptor);
    }
}
