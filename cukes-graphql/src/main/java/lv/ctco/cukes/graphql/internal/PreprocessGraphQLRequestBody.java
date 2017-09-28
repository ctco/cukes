package lv.ctco.cukes.graphql.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.graphql.facade.GQLRequestFacade;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;

@Singleton
public class PreprocessGraphQLRequestBody implements CukesHttpPlugin {

    @Inject
    GQLRequestFacade requestFacade;

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
        requestSpecification.body(requestFacade.getGraphQLRequest());
    }

    @Override
    public void afterRequest(Response response) {

    }
}
