package lv.ctco.cukes.core;

import lv.ctco.cukes.core.extension.AbstractCukesModule;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.RandomGeneratorFacadeImpl;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.facade.VariableFacadeImpl;

@CukesInjectableModule
public class TestCukesGuiceModule extends AbstractCukesModule {

    @Override
    protected void configure() {
        bind(VariableFacade.class).to(VariableFacadeImpl.class);
        bind(RandomGeneratorFacade.class).to(RandomGeneratorFacadeImpl.class);
    }
}
