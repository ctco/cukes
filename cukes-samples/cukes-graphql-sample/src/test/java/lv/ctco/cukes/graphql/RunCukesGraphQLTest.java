package lv.ctco.cukes.graphql;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber2.json"},
    features = {"classpath:features/conference/"},
    glue = "lv.ctco.cukes.graphql.api",
    strict = true
)
@Ignore
public class RunCukesGraphQLTest {
}
