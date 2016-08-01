package lv.ctco.cukesrest.api;

import com.google.inject.*;
import cucumber.api.java.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.*;

public class CucumberHooks {

    @Inject
    CucumberFacade cucumberFacade;

    @Before(order = CukesOptions.CUKES_BEFORE_HOOK_STARTUP_ORDER)
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
