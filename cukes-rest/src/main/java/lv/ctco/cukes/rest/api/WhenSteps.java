package lv.ctco.cukes.rest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import lv.ctco.cukes.http.facade.HttpResponseFacade;

@Singleton
public class WhenSteps {

    @Inject
    HttpResponseFacade facade;

    @When("^the client performs (.+) request on \"(.+)\"$")
    public void perform_Http_Request(String httpMethod, String url) throws Throwable {
        facade.setResponsePrefix("");
        facade.doRequest(httpMethod, url);
    }
}
