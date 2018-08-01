package lv.ctco.cukes.plugins;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber2.json"},
    features = {"classpath:features"},
    glue = {"lv.ctco.cukes"},
    strict = true
)
public class RunCukesTest {
}
