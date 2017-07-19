package lv.ctco.cukes.rabbitmq.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.rabbitmq.internal.ExchangeService;
import lv.ctco.cukes.rabbitmq.internal.MessageService;
import lv.ctco.cukes.rabbitmq.internal.MessageWrapper;

import java.util.Optional;

import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.CONTENT_TYPE;

@Singleton
public class RequestFacade {

    @Inject
    MessageService messageService;
    @Inject
    ExchangeService exchangeService;
    @Inject
    GlobalWorldFacade globalWorldFacade;

    private MessageWrapper message;

    @Inject
    public RequestFacade(GlobalWorldFacade globalWorldFacade) {
        this.globalWorldFacade = globalWorldFacade;
        initRequestMessage();
    }

    public void initRequestMessage() {
        message = new MessageWrapper();
        message.getProperties().contentType(globalWorldFacade.get(CONTENT_TYPE, null));
    }

    public void setBody(String body) {
        message.setBody(body);
    }

    public void setReplyTo(String replyTo) {
        message.getProperties().replyTo(replyTo);
    }

    public void setContentType(String contentType) {
        message.getProperties().contentType(contentType);
    }

    public MessageWrapper getMessage() {
        return message;
    }

    public void sendMessage(Optional<String> exchange, String routingKey) {
        messageService.sendMessage(exchange.orElse(exchangeService.getDefaultExchange()), routingKey, message);
    }
}
