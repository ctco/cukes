package lv.ctco.cukes.rabbitmq.sample.example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleSender {

    @Autowired
    RabbitTemplate template;

    public void send(String message) {
        System.out.println("Sending message: " +  message);
        template.convertAndSend(ExampleRoutingKeys.AS_STRING, message);
    }

}
