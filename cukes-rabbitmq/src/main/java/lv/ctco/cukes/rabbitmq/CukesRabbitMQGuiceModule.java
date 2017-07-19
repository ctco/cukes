package lv.ctco.cukes.rabbitmq;

import com.google.inject.AbstractModule;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.facade.VariableFacadeImpl;

@CukesInjectableModule
public class CukesRabbitMQGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(VariableFacade.class).to(VariableFacadeImpl.class);
    }
}
