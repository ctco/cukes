package lv.ctco.cukes.rabbitmq.sample.example;

import lv.ctco.cukes.rabbitmq.sample.configuration.RabbitMQConfiguration;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ExampleStringReceiver {

    public static final String ID = "exampleStringReceiver";

    @RabbitListener(id = ID, bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = RabbitMQConfiguration.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
                    key = ExampleRoutingKeys.AS_STRING
            )
    })
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
