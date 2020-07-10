package lv.ctco.cukes.ldap.sample;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber.json"},
    features = {"classpath:features/"},
    glue = {"lv.ctco.cukes.ldap", "lv.ctco.cukes.core.api"}
)
public class RunCukesLDAPTest {

    private static final CukesLDAPBootstrap bootstrap = new CukesLDAPBootstrap();

    @BeforeClass
    public static void setUp() {
        bootstrap.beforeAllTests();
    }

    @AfterClass
    public static void tearDown() {
        bootstrap.afterAllTests();
    }
}
