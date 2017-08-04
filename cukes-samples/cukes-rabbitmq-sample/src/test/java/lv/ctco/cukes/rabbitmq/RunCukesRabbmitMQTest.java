package lv.ctco.cukes.rabbitmq;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import lv.ctco.cukes.rabbitmq.sample.Application;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ConfigurableApplicationContext;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "json:target/cucumber.json", "lv.ctco.cukes.core.formatter.CukesJsonFormatter:target/cucumber2.json"},
        features = {"classpath:features/"},
        glue = {"lv.ctco.cukes.rabbitmq", "lv.ctco.cukes.core.api"},
        strict = true
)
public class RunCukesRabbmitMQTest {

    private static ConfigurableApplicationContext context;

    @BeforeClass
    public static void setUp() throws Exception {
        context = Application.run();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void name() throws Exception {
        System.out.println("aaaa");
    }
}
