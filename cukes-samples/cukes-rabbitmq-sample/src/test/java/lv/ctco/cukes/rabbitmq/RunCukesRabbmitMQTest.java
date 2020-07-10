package lv.ctco.cukes.rabbitmq;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lv.ctco.cukes.rabbitmq.sample.Application;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ConfigurableApplicationContext;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber2.json"},
    features = {"classpath:features/"},
    glue = {"lv.ctco.cukes.rabbitmq", "lv.ctco.cukes.core.api"}
)
public class RunCukesRabbmitMQTest {

    private static ConfigurableApplicationContext context;

    @BeforeClass
    public static void setUp() {
        context = Application.run();
    }

    @AfterClass
    public static void tearDown() {
        context.close();
    }

    @Test
    public void name() {
        System.out.println("aaaa");
    }
}
