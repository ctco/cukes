package lv.ctco.cukes.graphql;

import com.google.inject.Inject;
import cucumber.api.java.After;
import lv.ctco.cukes.graphql.facade.GQLRequestFacade;

public class CukesGraphQLHooks {

    @Inject
    GQLRequestFacade requestFacade;

    @After
    public void afterScenario() {
        requestFacade.initNewSpecification();
    }
}
