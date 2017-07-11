package lv.ctco.cukes.rabbitmq.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;

import java.io.IOException;

@Singleton
public class QueueService {

    @Inject
    ConnectionService connectionService;

    @SneakyThrows(IOException.class)
    public void declareQueue(String queueName, String exchange, String routingKey) {
        Channel channel = connectionService.getChannel();
        channel.queueDeclare(queueName, false, true,true, null);
        channel.queueBind(queueName, exchange, routingKey);
    }

}
