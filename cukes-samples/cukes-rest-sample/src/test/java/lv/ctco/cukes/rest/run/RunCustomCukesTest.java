package lv.ctco.cukes.rest.run;


import io.cucumber.guice.CucumberScopes;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lv.ctco.cukes.core.internal.di.SingletonObjectFactory;
import lv.ctco.cukes.rest.SampleApplication;
import lv.ctco.cukes.rest.run.custom.steps.CustomSteps;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = "classpath:features/custom/",
    glue = {"lv.ctco.cukes"}
)
public class RunCustomCukesTest {

    @BeforeClass
    public static void setUp() throws Exception {
        SingletonObjectFactory.instance().addModule(binder -> binder.bind(CustomSteps.StateSaver.class));

        new SampleApplication().run(new String[]{"server", "server.yml"});
    }
}
