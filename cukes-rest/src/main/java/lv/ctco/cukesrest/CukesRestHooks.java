package lv.ctco.cukesrest;

import com.google.inject.Inject;
import cucumber.api.java.After;
import lv.ctco.cukesrest.facade.RestRequestFacade;

public class CukesRestHooks {

    @Inject
    RestRequestFacade requestFacade;

    @After
    public void afterScenario() {
        requestFacade.initNewSpecification();
    }
}
