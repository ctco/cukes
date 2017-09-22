package lv.ctco.cukes.rest;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.RandomGeneratorFacadeImpl;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.facade.VariableFacadeImpl;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.http.logging.HttpLoggingPlugin;
import lv.ctco.cukes.rest.facade.RestAssertionFacade;
import lv.ctco.cukes.rest.facade.RestAssertionFacadeImpl;

import static lv.ctco.cukes.core.CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS;
import static lv.ctco.cukes.core.CukesOptions.PROPERTIES_PREFIX;

@CukesInjectableModule
public class CukesRestGuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        boolean isLoadRunnedEnabled = Boolean.parseBoolean(System.getProperty(PROPERTIES_PREFIX + LOADRUNNER_FILTER_BLOCKS_REQUESTS));
        if (!isLoadRunnedEnabled) {
            bind(RestAssertionFacade.class).to(RestAssertionFacadeImpl.class);
            bind(VariableFacade.class).to(VariableFacadeImpl.class);
            bind(RandomGeneratorFacade.class).to(RandomGeneratorFacadeImpl.class);
        }

        Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
        multibinder.addBinding().to(HttpLoggingPlugin.class);
    }
}
