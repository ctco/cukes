package lv.ctco.cukes.rabbitmq.sample.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Slf4j
public class RabbitMQConfiguration {

    public static final String EXCHANGE_NAME = "exchange";


    @Value("${rabbitmq.host}")
    String hostname;
    @Value("${rabbitmq.port}")
    int port;
    @Value("${rabbitmq.username}")
    String userName;
    @Value("${rabbitmq.password}")
    String password;
    @Value("${rabbitmq.virtual-host:/}")
    String virtualHost;
    @Value("${rabbitmq.use-ssl:false}")
    boolean useSSL;

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    @DependsOn("amqpBroker")
    CachingConnectionFactory connectionFactory() {
        log.info("Creating connection factory for MQ broker...");
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost(hostname);
        cf.setPort(port);
        cf.setUsername(userName);
        cf.setPassword(password);
        cf.setVirtualHost(virtualHost);
        cf.setAutomaticRecoveryEnabled(false);
        if (useSSL) {
            try {
                cf.useSslProtocol();
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                //TODO don't throw raw exceptions
                throw new RuntimeException(e);
            }
        }

        log.info("Connection factory created.");
        return new CachingConnectionFactory(cf);
    }

    @Bean
    RabbitAdmin rabbitAdmin(org.springframework.amqp.rabbit.connection.ConnectionFactory  cf) {
        return new RabbitAdmin(cf);
    }

    @Bean
    RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory  cf,
                                  ObjectMapper mapper) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setExchange(EXCHANGE_NAME);
        RetryTemplate retry = new RetryTemplate();
        ExponentialBackOffPolicy backOff = new ExponentialBackOffPolicy();
        backOff.setInitialInterval(1000);
        backOff.setMultiplier(1.5);
        backOff.setMaxInterval(60000);
        retry.setBackOffPolicy(backOff);
        template.setRetryTemplate(retry);
        template.setMessageConverter(new Jackson2JsonMessageConverter(mapper));
        return template;
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(org.springframework.amqp.rabbit.connection.ConnectionFactory  cf,
                                                                        ObjectMapper mapper) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf);
        factory.setMessageConverter(new Jackson2JsonMessageConverter(mapper));
        return factory;
    }

    @Bean
    Exchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

}
