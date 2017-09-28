package lv.ctco.cukes.rest.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.internal.templating.TemplatingEngine;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.rest.facade.RestRequestFacade;

@Singleton
public class PreprocessRestRequestBody implements CukesHttpPlugin {

    @Inject
    TemplatingEngine templatingEngine;
    @Inject
    RestRequestFacade requestFacade;

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
        String requestBody = this.requestFacade.getRequestBody();
        if (requestBody != null) {
            String processed = templatingEngine.processBody(requestBody);
            requestSpecification.body(processed);
        }
    }

    @Override
    public void afterRequest(Response response) {
        this.requestFacade.clearRequestBody();
    }
}
