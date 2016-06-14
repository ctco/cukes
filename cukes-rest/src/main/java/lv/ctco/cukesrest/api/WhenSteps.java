package lv.ctco.cukesrest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import lv.ctco.cukesrest.internal.ResponseFacade;

@Singleton
public class WhenSteps {

    @Inject
    ResponseFacade facade;

    @When("^the client performs (.+) request on (.+)$")
    public void perform_Http_Request(String httpMethod, String url) throws Throwable {
        facade.doRequest(httpMethod, url);
    }
}
