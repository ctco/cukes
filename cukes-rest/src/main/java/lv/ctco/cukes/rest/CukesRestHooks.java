package lv.ctco.cukes.rest;

import com.google.inject.Inject;
import cucumber.api.java.After;
import lv.ctco.cukes.rest.facade.RestRequestFacade;

public class CukesRestHooks {

    @Inject
    RestRequestFacade requestFacade;

    @After
    public void afterScenario() {
        requestFacade.initNewSpecification();
    }
}
