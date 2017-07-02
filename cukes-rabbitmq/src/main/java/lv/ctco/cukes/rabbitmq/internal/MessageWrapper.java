package lv.ctco.cukes.rabbitmq.internal;

import com.rabbitmq.client.AMQP;

public class MessageWrapper {

    private String body;
    private AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder();

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AMQP.BasicProperties.Builder getProperties() {
        return properties;
    }

}
