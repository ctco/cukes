package lv.ctco.cukes.graphql;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    format = {"pretty", "json:target/cucumber.json", "lv.ctco.cukes.core.formatter.CukesJsonFormatter:target/cucumber2.json"},
    features = {"classpath:features/conference/"},
    glue = "lv.ctco.cukes.graphql.api",
    strict = true
)
@Ignore
public class RunCukesGraphQLTest {
}
