package lv.ctco.cukes.rabbitmq.sample.listeners;

import lv.ctco.cukes.rabbitmq.sample.configuration.RabbitMQConfiguration;
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
public class ToUpperCase {

    @Autowired
    RabbitTemplate template;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = RabbitMQConfiguration.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
                    key = "upper"
            )
    })
    public void onMessage(Message message) {
        String text = new String(message.getBody());
        System.out.println("ToUpperCase.onMessage - " + text);
        String result = text.toUpperCase();
        template.convertAndSend(message.getMessageProperties().getReplyTo(), result);
    }
}
