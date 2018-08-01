package lv.ctco.cukes.core;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json"},
    features = {"classpath:features"},
    glue = {"lv.ctco.cukes"},
    strict = true
)
public class RunCukesTest {
}
