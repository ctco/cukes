package lv.ctco.cukes.core.internal.di;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.core.internal.context.CaptureContext;
import lv.ctco.cukes.core.internal.context.CaptureContextInterceptor;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.context.InflateContextInterceptor;
import lv.ctco.cukes.core.internal.switches.SwitchedBy;
import lv.ctco.cukes.core.internal.switches.SwitchedByInterceptor;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Properties;

import static lv.ctco.cukes.core.internal.helpers.Files.createCukesPropertyFileUrl;

public class CukesGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(new InflateContextInterceptor(), InflateContext.class);
        bindInterceptor(new CaptureContextInterceptor(), CaptureContext.class);
        bindInterceptor(new SwitchedByInterceptor(), SwitchedBy.class);

        bindPlugins();
    }

    private void bindInterceptor(MethodInterceptor interceptor, Class<? extends Annotation> annotationType) {
        requestInjection(interceptor);
        bindInterceptor(Matchers.annotatedWith(annotationType), Matchers.any(), interceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(annotationType), interceptor);
    }

    @SuppressWarnings("unchecked")
    private void bindPlugins() {
        try {
            Multibinder<CukesPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesPlugin.class);

            // add user configured plugins
            ClassLoader classLoader = CukesGuiceModule.class.getClassLoader();

            Properties properties = new Properties();
            URL url = createCukesPropertyFileUrl(classLoader);
            if (url == null) return;
            properties.load(url.openStream());

            String plugins = properties.getProperty(CukesOptions.PROPERTIES_PREFIX + CukesOptions.PLUGINS);
            if (plugins == null) return;

            String[] pluginClasses = plugins.split(CukesOptions.DELIMITER);
            for (String pluginClass : pluginClasses) {
                Class<? extends CukesPlugin> aClass = (Class<? extends CukesPlugin>) classLoader.loadClass(pluginClass);
                multibinder.addBinding().to(aClass);
            }
        } catch (Exception e) {
            throw new CukesRuntimeException("Binding of Cukes plugins failed");
        }
    }
}
