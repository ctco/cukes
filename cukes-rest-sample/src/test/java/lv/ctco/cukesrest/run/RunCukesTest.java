package lv.ctco.cukesrest.run;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import lv.ctco.cukesrest.SampleApplication;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    format = {"pretty", "json:target/cucumber.json", "lv.ctco.cukesrest.formatter.CukesRestJsonFormatter:target/cucumber2.json"},
    features = "classpath:features",
    glue = "lv.ctco.cukesrest.api",
    strict = true
)
public class RunCukesTest {

    @BeforeClass
    public static void setUp() throws Exception {
        new SampleApplication().run(new String[]{"server", "server.yml"});
    }
}
