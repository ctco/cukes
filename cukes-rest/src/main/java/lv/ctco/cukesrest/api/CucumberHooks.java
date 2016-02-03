package lv.ctco.cukesrest.api;

import com.google.inject.*;
import cucumber.api.java.*;
import lv.ctco.cukesrest.internal.*;

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
