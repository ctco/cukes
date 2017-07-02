package lv.ctco.cukes.rabbitmq.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;

import java.io.IOException;

@Singleton
public class ExchangeService {

    private static final String DEFAULT_EXCHANGE_NAME_ATTRIBUTE = "_defaultExchange";

    @Inject
    ConnectionService connectionService;
    @Inject
    GlobalWorldFacade globalWorldFacade;

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
    public void declareExchange(String exchange, String type) {
        getChannel().exchangeDeclare(exchange, type);
    }

    @SneakyThrows(IOException.class)
    public void declareQueue(String queueName, String exchange, String routingKey) {
        Channel channel = getChannel();
        channel.queueDeclare(queueName, false, true,true, null);
        channel.queueBind(queueName, exchange, routingKey);
    }

}
