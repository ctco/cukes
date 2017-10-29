package lv.ctco.cukes.graphql.internal;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.internal.templating.TemplatingEngine;
import lv.ctco.cukes.graphql.facade.GQLRequestFacade;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;

@Singleton
public class PreprocessGraphQLRequestBody implements CukesHttpPlugin {

    @Inject
    GQLRequestFacade requestFacade;

    @Inject
    TemplatingEngine templatingEngine;

    @Override
    public void beforeAllTests() {

    }

    @Override
    public void afterAllTests() {

    }

    @Override
    public void beforeScenario() {

    }

    @Override
    public void afterScenario() {

    }

    @Override
    public void beforeRequest(RequestSpecification requestSpecification) {
        if (!Strings.isNullOrEmpty(requestFacade.getGraphQLRequest().getQuery())) {
            requestFacade.getGraphQLRequest().setQuery(templatingEngine.processBody(requestFacade.getGraphQLRequest().getQuery()));
        }
        if (!Strings.isNullOrEmpty(requestFacade.getGraphQLRequest().getVariables())) {
            requestFacade.getGraphQLRequest().setVariables(templatingEngine.processBody(requestFacade.getGraphQLRequest().getVariables()));
        }
        requestSpecification.body(requestFacade.getGraphQLRequest());
    }

    @Override
    public void afterRequest(Response response) {

    }
}
