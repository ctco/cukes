package lv.ctco.cukes.rabbitmq.api.given;

import cucumber.api.java.en.Given;
import lv.ctco.cukes.rabbitmq.api.NamePatterns;

public class ExchangeConfigurationSteps {

    @Given("^declare exchange \"" + NamePatterns.EXCHANGE_NAME + "\"$")
    public void declareExchange(String exchange) {
    }

    @Given("^declare exchange \"" + NamePatterns.EXCHANGE_NAME + "\" of type \"(topic|direct|fanout)\"$")
    public void declareExchangeWithType(String exchange, String type) {
    }

    @Given("^use exchange \"" + NamePatterns.EXCHANGE_NAME + "\" by default$")
    public void useExchangeByDefault(String exchange) {

    }
}
