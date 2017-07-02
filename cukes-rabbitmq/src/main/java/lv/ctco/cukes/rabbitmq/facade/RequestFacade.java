package lv.ctco.cukes.rabbitmq.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.rabbitmq.internal.ExchangeService;
import lv.ctco.cukes.rabbitmq.internal.MessageService;
import lv.ctco.cukes.rabbitmq.internal.MessageWrapper;

import java.util.Optional;

@Singleton
public class RequestFacade {

    @Inject
    MessageService messageService;
    @Inject
    ExchangeService exchangeService;

    private MessageWrapper message;

    public RequestFacade() {
        initRequestMessage();
    }

    public void initRequestMessage() {
        message = new MessageWrapper();
    }

    public void setBody(String body) {
        message.setBody(body);
    }

    public void setReplyTo(String replyTo) {
        message.getProperties().replyTo(replyTo);
    }

    public MessageWrapper getMessage() {
        return message;
    }

    public void sendMessage(Optional<String> exchange, String routingKey) {
        messageService.sendMessage(exchange.orElse(exchangeService.getDefaultExchange()), routingKey, message);
    }
}
