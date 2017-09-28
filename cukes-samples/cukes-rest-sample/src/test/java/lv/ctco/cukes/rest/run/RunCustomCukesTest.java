package lv.ctco.cukes.rest.run;


import com.google.inject.Binder;
import com.google.inject.Module;
import cucumber.api.CucumberOptions;
import cucumber.api.guice.CucumberScopes;
import cucumber.api.junit.Cucumber;
import lv.ctco.cukes.core.internal.di.SingletonObjectFactory;
import lv.ctco.cukes.rest.SampleApplication;
import lv.ctco.cukes.rest.run.custom.steps.CustomSteps;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    format = {"pretty"},
    features = "classpath:features/custom/",
    glue = {"lv.ctco.cukes"},
    strict = true
)
public class RunCustomCukesTest {

    @BeforeClass
    public static void setUp() throws Exception {
        SingletonObjectFactory.instance().addModule(binder -> binder.bind(CustomSteps.StateSaver.class).in(CucumberScopes.SCENARIO));

        new SampleApplication().run(new String[]{"server", "server.yml"});
    }
}
