package lv.ctco.cukes.rest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import lv.ctco.cukes.rest.facade.RestResponseFacade;

@Singleton
public class WhenSteps {

    @Inject
    RestResponseFacade facade;

    @When("^the client performs (.+) request on \"(.+)\"$")
    public void perform_Http_Request(String httpMethod, String url) throws Throwable {
        facade.doRequest(httpMethod, url);
    }
}
