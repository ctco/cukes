package lv.ctco.cukes.rabbitmq;

public class ConfigurationParameters {
    // Connection
    public static final String HOST = "rabbitmq.host";
    public static final String PORT = "rabbitmq.port";
    public static final String USER = "rabbitmq.user";
    public static final String PASSWORD = "rabbitmq.password";
    public static final String VIRTUAL_HOST = "rabbitmq.vhost";

    // Defaults
    public static final String DEFAULT_EXCHANGE_NAME_ATTRIBUTE = "rabbitmq.exchange.default";
    public static final String DEFAULT_READ_TIMEOUT = "rabbitmq.read-timeout.default";
    public static final String CONTENT_TYPE = "rabbitmq.content-type";

}
