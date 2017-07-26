package lv.ctco.cukes.rabbitmq.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.DEFAULT_EXCHANGE_NAME_ATTRIBUTE;

@Singleton
public class ExchangeService {

    @Inject
    ConnectionService connectionService;
    @Inject
    GlobalWorldFacade globalWorldFacade;

    @Inject
    public ExchangeService(ConnectionService connectionService, GlobalWorldFacade globalWorldFacade) {
        this.connectionService = connectionService;
        this.globalWorldFacade = globalWorldFacade;
        initDefaultExchanges();
    }

    private void initDefaultExchanges() {
        Pattern propertyPattern = Pattern.compile("rabbitmq.exchange\\.(\\d+)\\.(name|type|durable)");
        Set<String> keys = new TreeSet<>(this.globalWorldFacade.getKeysStartingWith("rabbitmq.exchange"));
        Set<String> ids = keys.stream().
                map(propertyPattern::matcher).
                filter(Matcher::matches).
                map(matcher -> matcher.group(1)).
                collect(Collectors.toSet());
        for (String id : ids) {
            String name = this.globalWorldFacade.get("rabbitmq.exchange." + id + ".name").or(() -> {
                throw new IllegalArgumentException("No name specified for predefined exchange: rabbitmq.exchange." + id + ".name");
            });
            String type = this.globalWorldFacade.get("rabbitmq.exchange." + id + ".type", "direct");
            boolean durable = this.globalWorldFacade.getBoolean("rabbitmq.exchange." + id + ".durable", false);
            declareExchange(name, type, durable);
        }

    }

    public void setDefaultExchange(String exchange) {
        globalWorldFacade.put(DEFAULT_EXCHANGE_NAME_ATTRIBUTE, exchange);
    }

    public String getDefaultExchange() {
        return globalWorldFacade.get(DEFAULT_EXCHANGE_NAME_ATTRIBUTE).orNull();
    }

    private Channel getChannel() {
        return connectionService.getChannel();
    }

    @SneakyThrows(IOException.class)
    public void declareExchange(String exchange, String type, boolean durable) {
        getChannel().exchangeDeclare(exchange, type, durable);
    }

    @SneakyThrows(IOException.class)
    public void declareQueue(String queueName, String exchange, String routingKey) {
        Channel channel = getChannel();
        channel.queueDeclare(queueName, false, true, true, null);
        channel.queueBind(queueName, exchange, routingKey);
    }

}
