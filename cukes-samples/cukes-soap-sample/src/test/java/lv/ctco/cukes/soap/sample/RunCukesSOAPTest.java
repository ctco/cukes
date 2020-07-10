package lv.ctco.cukes.soap.sample;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber2.json"},
    features = {"classpath:features/"},
    glue = {"lv.ctco.cukes"}
)
public class RunCukesSOAPTest {

}
