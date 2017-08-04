package lv.ctco.cukes.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Ignore;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Ignore
public class E2ETest {

    public static final String EXCHANGE = "exchange";
    public static final String ROUTING_KEY = "prepend";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //Setup
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("default");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, "topic");

        //Setting up output
        String outQueue = channel.queueDeclare().getQueue();
        channel.queueBind(outQueue, EXCHANGE, "out");

        //Create consumer (listener)
        BlockingQueue<String> result = new ArrayBlockingQueue<String>(1);
        channel.basicConsume(outQueue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String response = new String(body);
                result.add(response);
            }
        });

        //Prepare and publish message
        String message = "{\"body\": \"world\"}";
        AMQP.BasicProperties props = new AMQP.BasicProperties()
                .builder()
                .replyTo("out")
                .contentType("application/json")
                .build();
        channel.basicPublish(EXCHANGE, ROUTING_KEY, props, message.getBytes());

        //Get response
        String response = result.poll(10, TimeUnit.SECONDS);
        System.out.println("Got response: " + response);

        //Finalize
        channel.close();
        connection.close();
    }
}
