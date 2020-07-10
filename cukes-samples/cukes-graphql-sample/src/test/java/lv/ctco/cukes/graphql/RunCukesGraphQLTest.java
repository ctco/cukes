package lv.ctco.cukes.graphql;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "json:target/cucumber2.json"},
    features = {"classpath:features/conference/"},
    glue = "lv.ctco.cukes.graphql.api"
)
@Ignore
public class RunCukesGraphQLTest {
}
