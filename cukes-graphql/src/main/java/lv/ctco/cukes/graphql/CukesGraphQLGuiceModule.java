package lv.ctco.cukes.graphql;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.RandomGeneratorFacadeImpl;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.facade.VariableFacadeImpl;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.http.logging.HttpLoggingPlugin;

@CukesInjectableModule
public class CukesGraphQLGuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(VariableFacade.class).to(VariableFacadeImpl.class);
        bind(RandomGeneratorFacade.class).to(RandomGeneratorFacadeImpl.class);

        Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
        multibinder.addBinding().to(HttpLoggingPlugin.class);
    }
}
