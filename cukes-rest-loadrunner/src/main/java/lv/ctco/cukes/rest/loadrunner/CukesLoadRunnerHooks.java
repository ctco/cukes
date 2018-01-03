package lv.ctco.cukes.rest.loadrunner;

import com.google.inject.Inject;
import cucumber.api.java.Before;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.http.facade.HttpRequestFacade;

public class CukesLoadRunnerHooks {

    @Inject
    HttpRequestFacade requestFacade;

    @Inject
    GlobalWorldFacade world;

    @Before
    public void beforeLoadRunnerScenario() {
        boolean filterEnabled = world.getBoolean(CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS);
        if (filterEnabled) {
            requestFacade.initNewSpecification();
        }
    }

}
