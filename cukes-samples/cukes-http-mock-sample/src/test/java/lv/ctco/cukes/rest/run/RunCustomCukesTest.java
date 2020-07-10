package lv.ctco.cukes.rest.run;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = "classpath:features/custom/",
    glue = {"lv.ctco.cukes"}
)
public class RunCustomCukesTest {
}
