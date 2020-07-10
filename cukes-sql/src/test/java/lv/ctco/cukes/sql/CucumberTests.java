package lv.ctco.cukes.sql;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    tags = "not @Ignore",
    features = {"src/test/resources/features"},
    glue = "lv.ctco.cukes",
    plugin = {"pretty", "junit:build/cucumber-report/junit.xml", "json:build/cucumber-report/report.json"},
    monochrome = true
)
public class CucumberTests {
}
