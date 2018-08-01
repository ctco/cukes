package lv.ctco.cukes.oauth.sample;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber.json"},
    features = {"classpath:features/"},
    glue = {"lv.ctco.cukes"},
    strict = true
)
public class RunCukesOAuthTest {

    private static CukesOAuthBootstrap bootstrap = new CukesOAuthBootstrap();

    @BeforeClass
    public static void setUp() throws Exception {
        bootstrap.beforeAllTests();
    }

    @AfterClass
    public static void tearDown() {
        bootstrap.afterAllTests();
    }
}
