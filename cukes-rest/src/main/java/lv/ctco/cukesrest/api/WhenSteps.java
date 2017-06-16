package lv.ctco.cukesrest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import lv.ctco.cukesrest.facade.RestResponseFacade;

@Singleton
public class WhenSteps {

    @Inject
    RestResponseFacade facade;

    @When("^the client performs (.+) request on \"(.+)\"$")
    public void perform_Http_Request(String httpMethod, String url) throws Throwable {
        facade.doRequest(httpMethod, url);
    }
}
