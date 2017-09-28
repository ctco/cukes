package lv.ctco.cukes.http;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.RandomGeneratorFacadeImpl;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.facade.VariableFacadeImpl;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.http.facade.HttpAssertionFacade;
import lv.ctco.cukes.http.facade.HttpAssertionFacadeImpl;
import lv.ctco.cukes.http.logging.HttpLoggingPlugin;

import static lv.ctco.cukes.core.CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS;
import static lv.ctco.cukes.core.CukesOptions.PROPERTIES_PREFIX;

@CukesInjectableModule
public class CukesHttpGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        boolean isLoadRunnedEnabled = Boolean.parseBoolean(System.getProperty(PROPERTIES_PREFIX + LOADRUNNER_FILTER_BLOCKS_REQUESTS));
        if (!isLoadRunnedEnabled) {
            bind(HttpAssertionFacade.class).to(HttpAssertionFacadeImpl.class);
            bind(VariableFacade.class).to(VariableFacadeImpl.class);
            bind(RandomGeneratorFacade.class).to(RandomGeneratorFacadeImpl.class);
        }
        Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
        multibinder.addBinding().to(HttpLoggingPlugin.class);
    }
}
