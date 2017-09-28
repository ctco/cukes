package lv.ctco.cukes.rest.loadrunner;

import com.google.inject.AbstractModule;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.http.facade.HttpAssertionFacade;

import static lv.ctco.cukes.core.CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS;
import static lv.ctco.cukes.core.CukesOptions.PROPERTIES_PREFIX;

@CukesInjectableModule
public class CukesRestLoadRunnerGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        boolean isLoadRunnedEnabled = Boolean.parseBoolean(System.getProperty(PROPERTIES_PREFIX + LOADRUNNER_FILTER_BLOCKS_REQUESTS));
        if (isLoadRunnedEnabled) {
            bind(HttpAssertionFacade.class).to(HttpAssertionFacadeLoadRunnerImpl.class);
            bind(VariableFacade.class).to(VariableFacadeLoadRunnerImpl.class);
            bind(RandomGeneratorFacade.class).to(RandomGeneratorLoadRunnerImpl.class);
        }
    }
}
