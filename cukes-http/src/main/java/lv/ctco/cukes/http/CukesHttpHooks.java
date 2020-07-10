package lv.ctco.cukes.http;

import com.google.inject.Inject;
import io.cucumber.java.After;
import lv.ctco.cukes.http.facade.HttpRequestFacade;

public class CukesHttpHooks {

    @Inject
    HttpRequestFacade requestFacade;

    @After
    public void afterScenario() {
        requestFacade.initNewSpecification();
    }
}
