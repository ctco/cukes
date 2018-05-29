package lv.ctco.cukes.sql;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    tags        = {"~@Ignore"},
    features    = {"src/test/resources/features"},
    glue        = "lv.ctco.cukes",
    plugin      = {"pretty", "junit:build/cucumber-report/junit.xml", "json:build/cucumber-report/report.json"},
    strict      = true,
    monochrome  = true
)
public class CucumberTests {
}
