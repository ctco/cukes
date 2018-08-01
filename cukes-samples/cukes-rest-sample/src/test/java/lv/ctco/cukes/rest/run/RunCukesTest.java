package lv.ctco.cukes.rest.run;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import lv.ctco.cukes.rest.SampleApplication;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber2.json"},
    features = {"classpath:features/gadgets/", "classpath:features/healthcheck/", "classpath:features/variables/", "classpath:features/multipart/"},
    glue = "lv.ctco.cukes",
    strict = true
)
public class RunCukesTest {

    @BeforeClass
    public static void setUp() throws Exception {
        new SampleApplication().run(new String[]{"server", "server.yml"});
    }
}
