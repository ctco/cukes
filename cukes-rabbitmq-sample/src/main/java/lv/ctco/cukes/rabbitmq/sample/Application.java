package lv.ctco.cukes.rabbitmq.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) throws IOException {
        run();
    }

    public static ConfigurableApplicationContext run() {
        return SpringApplication.run(Application.class,
                "--rabbitmq.host=localhost",
                "--rabbitmq.port=5672",
                "--rabbitmq.username=guest",
                "--rabbitmq.password=guest",
                "--rabbitmq.virtual-host=default",
                "--rabbitmq.use-ssl=false"
        );
    }
}
