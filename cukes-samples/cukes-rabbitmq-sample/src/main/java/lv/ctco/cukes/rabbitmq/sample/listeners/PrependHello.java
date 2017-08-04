package lv.ctco.cukes.rabbitmq.sample.listeners;

import lv.ctco.cukes.rabbitmq.sample.configuration.RabbitMQConfiguration;
import lv.ctco.cukes.rabbitmq.sample.message.StringMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrependHello {

    @Autowired
    RabbitTemplate template;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = RabbitMQConfiguration.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
                    key = "prepend"
            )
    })
    public void onMessage(StringMessage msg, Message message) {
        String text = msg.getBody();
        System.out.println("PrependHello.onMessage - " + text);
        String result = "hello, " + text;
        template.convertAndSend(message.getMessageProperties().getReplyTo(), new StringMessage(result));
    }
}
