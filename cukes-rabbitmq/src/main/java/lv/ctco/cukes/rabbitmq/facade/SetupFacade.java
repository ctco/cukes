package lv.ctco.cukes.rabbitmq.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.rabbitmq.internal.ConnectionService;
import lv.ctco.cukes.rabbitmq.internal.ExchangeService;
import lv.ctco.cukes.rabbitmq.internal.QueueService;

import java.util.Optional;

@Singleton
public class SetupFacade {

    @Inject
    ConnectionService connectionService;
    @Inject
    ExchangeService exchangeService;
    @Inject
    QueueService queueService;

    public void setHost(String host) {
        connectionService.setHost(host);
    }

    public void setPort(int port) {
        connectionService.setPort(port);
    }

    public void setUsername(String username) {
        connectionService.setUsername(username);
    }

    public void setPassword(String password) {
        connectionService.setPassword(password);
    }

    public void setVirtualHost(String virtualHost) {
        connectionService.setVirtualHost(virtualHost);
    }

    public void setSsl(boolean ssl) {
        connectionService.setSsl(ssl);
    }

    public void declareExchange(String name, String type, boolean durable) {
        exchangeService.declareExchange(name, type, durable);
    }

    public void setDefaultExchange(String exchange) {
        exchangeService.setDefaultExchange(exchange);
    }

    public void declareQueue(String queueName, Optional<String> exchange, String routingKey) {
        queueService.declareQueue(queueName, exchange.orElse(exchangeService.getDefaultExchange()), routingKey);
    }
}
