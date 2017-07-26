package lv.ctco.cukes.rabbitmq.api.given;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;
import lv.ctco.cukes.rabbitmq.facade.SetupFacade;

@Singleton
public class ExchangeConfigurationSteps {

    @Inject
    SetupFacade setupFacade;

    @Given("^declare( durable)? exchange \"" + NamePatterns.EXCHANGE_NAME + "\"$")
    public void declareExchange(String durable, String exchange) {
        this.declareExchangeWithType(durable, exchange, "direct");
    }

    @Given("^declare( durable)? exchange \"" + NamePatterns.EXCHANGE_NAME + "\" of type \"(topic|direct|fanout)\"$")
    public void declareExchangeWithType(String durable, String exchange, String type) {
        setupFacade.declareExchange(exchange, type, durable != null);
    }

    @Given("^use exchange \"" + NamePatterns.EXCHANGE_NAME + "\" by default$")
    public void useExchangeByDefault(String exchange) {
        setupFacade.setDefaultExchange(exchange);
    }
}
