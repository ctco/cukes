package lv.ctco.cukes.rabbitmq.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.HOST;
import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.PASSWORD;
import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.PORT;
import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.USER;
import static lv.ctco.cukes.rabbitmq.ConfigurationParameters.VIRTUAL_HOST;

@Singleton
public class ConnectionService {


    GlobalWorldFacade globalWorldFacade;

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    @Inject
    public ConnectionService(GlobalWorldFacade globalWorldFacade) {
        this.globalWorldFacade = globalWorldFacade;
        this.factory = new ConnectionFactory();
        initConfiguration();
    }

    public void setHost(String host) {
        setConnectionFactoryParameter(this.factory::setHost, host);
    }

    public void setPort(Integer port) {
        setConnectionFactoryParameter(this.factory::setPort, port);
    }

    public void setUsername(String username) {
        setConnectionFactoryParameter(this.factory::setUsername, username);
    }

    public void setPassword(String password) {
        setConnectionFactoryParameter(this.factory::setPassword, password);
    }

    public void setVirtualHost(String virtualHost) {
        setConnectionFactoryParameter(this.factory::setVirtualHost, virtualHost);
    }

    private <T> void setConnectionFactoryParameter(Consumer<T> setter, T value) {
        if (value == null) {
            return;
        }
        invalidate();
        setter.accept(value);
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

    public void initConfiguration() {
        setHost(globalWorldFacade.get(HOST, "localhost"));
        setPort(Integer.parseInt(globalWorldFacade.get(PORT, "5672")));
        setUsername(globalWorldFacade.get(USER, "guest"));
        setPassword(globalWorldFacade.get(PASSWORD, "password"));
        setVirtualHost(globalWorldFacade.get(VIRTUAL_HOST, "default"));
    }

}
