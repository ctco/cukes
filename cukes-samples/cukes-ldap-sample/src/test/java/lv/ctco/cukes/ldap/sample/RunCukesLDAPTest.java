package lv.ctco.cukes.ldap.sample;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    format = {"pretty", "json:target/cucumber.json", "lv.ctco.cukes.core.formatter.CukesJsonFormatter:target/cucumber.json"},
    features = {"classpath:features/"},
    glue = {"lv.ctco.cukes.ldap", "lv.ctco.cukes.core.api"},
    strict = true
)
public class RunCukesLDAPTest {

    private static CukesLDAPBootstrap bootstrap = new CukesLDAPBootstrap();

    @BeforeClass
    public static void setUp() throws Exception {
        bootstrap.beforeAllTests();
    }

    @AfterClass
    public static void tearDown() {
        bootstrap.afterAllTests();
    }
}
