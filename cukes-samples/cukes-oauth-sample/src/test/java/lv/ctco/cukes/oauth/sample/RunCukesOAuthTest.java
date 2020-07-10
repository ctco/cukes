package lv.ctco.cukes.oauth.sample;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber.json"},
    features = {"classpath:features/"},
    glue = {"lv.ctco.cukes"}
)
public class RunCukesOAuthTest {

    private static final CukesOAuthBootstrap bootstrap = new CukesOAuthBootstrap();

    @BeforeClass
    public static void setUp() {
        bootstrap.beforeAllTests();
    }

    @AfterClass
    public static void tearDown() {
        bootstrap.afterAllTests();
    }
}
