package lv.ctco.cukes.rest.run;

import cucumber.api.*;
import lv.ctco.cukes.rest.loadrunner.junit.CucumberLoadRunner;
import org.junit.runner.*;

@RunWith(CucumberLoadRunner.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = "classpath:features",
    glue = {"lv.ctco.cukes.core.api", "lv.ctco.cukes.rest.loadrunner"},
    strict = true
)
public class RunLoadrunnerGenerator {}
