package lv.ctco.cukes.rabbitmq.internal;

import com.rabbitmq.client.AMQP;

public class MessageWrapper {

    private String body;
    private AMQP.BasicProperties.Builder properties;

    public MessageWrapper() {
        this.properties = new AMQP.BasicProperties().builder();
    }

    public MessageWrapper(String body, AMQP.BasicProperties properties) {
        this.body = body;
        this.properties = properties.builder();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AMQP.BasicProperties.Builder getProperties() {
        return properties;
    }

    public String getContentType() {
        return properties.build().getContentType();
    }

}
