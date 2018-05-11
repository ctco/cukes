package lv.ctco.cukes.rest.run;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    format = {"pretty"},
    features = "classpath:features/custom/",
    glue = {"lv.ctco.cukes"},
    strict = true
)
public class RunCustomCukesTest {
}
