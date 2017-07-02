package lv.ctco.cukes.rabbitmq.internal;

import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;
import lv.ctco.cukesrest.CukesRuntimeException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Singleton
public class ConnectionService {

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public ConnectionService() {
        this.factory = new ConnectionFactory();
    }

    public void setHost(String host) {
        invalidate();
        this.factory.setHost(host);
    }

    public void setPort(int port) {
        invalidate();
        this.factory.setPort(port);
    }

    public void setUsername(String username) {
        invalidate();
        this.factory.setUsername(username);
    }

    public void setPassword(String password) {
        invalidate();
        this.factory.setPassword(password);
    }

    public void setVirtualHost(String virtualHost) {
        invalidate();
        this.factory.setVirtualHost(virtualHost);
    }

    public void setSsl(boolean ssl) {
        if (ssl) {
            invalidate();
            try {
                this.factory.useSslProtocol();
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                throw new CukesRuntimeException(e);
            }
        }
    }

    @SneakyThrows({IOException.class, TimeoutException.class})
    private void invalidate() {
        if (channel != null) {
            channel.close();
            channel = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    @SneakyThrows({IOException.class, TimeoutException.class})
    private Connection getConnection() {
        if (connection == null) {
            connection = factory.newConnection();
        }
        return connection;
    }

    @SneakyThrows(IOException.class)
    public Channel getChannel() {
        if (channel == null) {
            channel = getConnection().createChannel();
        }
        return channel;
    }

}
