package lv.ctco.cukesrest.run;

import cucumber.api.*;
import lv.ctco.cukesrest.loadrunner.junit.CucumberLoadRunner;
import org.junit.runner.*;

@RunWith(CucumberLoadRunner.class)
@CucumberOptions(
    format = {"pretty"},
    features = "classpath:features",
    glue = {"lv.ctco.cukesrest.api", "lv.ctco.cukesrest.loadrunner"},
    strict = true
)
public class RunLoadrunnerGenerator {}
