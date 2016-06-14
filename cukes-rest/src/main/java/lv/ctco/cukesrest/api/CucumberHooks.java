package lv.ctco.cukesrest.api;

import com.google.inject.Inject;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lv.ctco.cukesrest.internal.CucumberFacade;

public class CucumberHooks {

    @Inject
    CucumberFacade cucumberFacade;

    @Before
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
