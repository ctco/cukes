package lv.ctco.cukes.sql;

import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.RandomGeneratorFacadeImpl;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.facade.VariableFacadeImpl;
import lv.ctco.cukes.core.internal.di.CukesGuiceModule;

@CukesInjectableModule
public class TestModule extends CukesGuiceModule {

    @Override
    protected void configure() {
        super.configure();
        bind(ConnectionFactory.class).to(H2MemoryDatabase.class);
        bind(VariableFacade.class).to(VariableFacadeImpl.class);
        bind(RandomGeneratorFacade.class).to(RandomGeneratorFacadeImpl.class);
    }
}
