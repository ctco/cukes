package lv.ctco.cukes.rabbitmq.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Singleton
public class MessageService {

    @Inject
    ConnectionService connectionService;

    @SneakyThrows(IOException.class)
    public void sendMessage(String exchange, String routingKey, MessageWrapper message) {
        Channel channel = connectionService.getChannel();
        channel.basicPublish(exchange, routingKey, message.getProperties().build(), message.getBody().getBytes());
    }

    @SneakyThrows({IOException.class, InterruptedException.class})
    public MessageWrapper receiveMessage(String queue, int timeoutInSeconds) {
        Channel channel = connectionService.getChannel();
        BlockingQueue<MessageWrapper> result = new ArrayBlockingQueue<MessageWrapper>(1);
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String response = new String(body);
                MessageWrapper messageWrapper = new MessageWrapper(response, properties);
                result.add(messageWrapper);
            }
        });
        return result.poll(timeoutInSeconds, TimeUnit.SECONDS);
    }
}
