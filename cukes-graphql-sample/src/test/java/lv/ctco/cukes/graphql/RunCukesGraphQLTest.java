package lv.ctco.cukes.graphql;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    format = {"pretty", "json:target/cucumber.json", "lv.ctco.cukes.core.formatter.CukesJsonFormatter:target/cucumber2.json"},
    features = {"classpath:features/gadgets/"},
    glue = "lv.ctco.cukes.graphql.api",
    strict = true
)
public class RunCukesGraphQLTest {
}
