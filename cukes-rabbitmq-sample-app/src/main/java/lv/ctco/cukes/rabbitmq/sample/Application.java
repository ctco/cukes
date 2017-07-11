package lv.ctco.cukes.rabbitmq.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class,
                "--rabbitmq.host=localhost",
                "--rabbitmq.port=5672",
                "--rabbitmq.username=guest",
                "--rabbitmq.password=guest",
                "--rabbitmq.virtual-host=default",
                "--rabbitmq.use-ssl=false"
        );
    }
}
