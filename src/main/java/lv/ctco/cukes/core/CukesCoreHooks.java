package lv.ctco.cukes.core;

import com.google.inject.Inject;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lv.ctco.cukes.core.internal.CucumberFacade;

public class CukesCoreHooks {

    @Inject
    CucumberFacade cucumberFacade;

    @Before(order = 500)
    public void beforeScenario() {
        if (cucumberFacade.firstScenario()) {
            cucumberFacade.beforeAllTests();
        }
        cucumberFacade.beforeScenario();
    }

    @After
    public void afterScenario() {
        cucumberFacade.afterScenario();
    }
}
