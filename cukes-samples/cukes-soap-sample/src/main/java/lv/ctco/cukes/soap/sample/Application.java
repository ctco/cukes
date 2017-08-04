package lv.ctco.cukes.soap.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ws.config.annotation.EnableWs;

@SpringBootApplication
@EnableWs
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
