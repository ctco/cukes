package lv.ctco.cukesrest;

import com.google.inject.AbstractModule;
import lv.ctco.cukescore.extension.CukesInjectableModule;
import lv.ctco.cukesrest.facade.RestAssertionFacade;
import lv.ctco.cukesrest.facade.RestAssertionFacadeImpl;
import lv.ctco.cukescore.facade.VariableFacade;
import lv.ctco.cukescore.facade.VariableFacadeImpl;

import static lv.ctco.cukescore.CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS;
import static lv.ctco.cukescore.CukesOptions.PROPERTIES_PREFIX;


@CukesInjectableModule
public class CukesRestGuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        boolean isLoadRunnedEnabled = Boolean.valueOf(System.getProperty(PROPERTIES_PREFIX + LOADRUNNER_FILTER_BLOCKS_REQUESTS));
        if (!isLoadRunnedEnabled) {
            bind(RestAssertionFacade.class).to(RestAssertionFacadeImpl.class);
            bind(VariableFacade.class).to(VariableFacadeImpl.class);
        }
    }
}
