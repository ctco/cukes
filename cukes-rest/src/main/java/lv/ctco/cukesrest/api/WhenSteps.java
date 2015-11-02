package lv.ctco.cukesrest.api;

import com.google.inject.*;
import cucumber.api.java.en.*;
import lv.ctco.cukesrest.internal.*;

@Singleton
public class WhenSteps {

    @Inject
    ResponseFacade facade;

    @When("^the client performs (.+) request on (.+)$")
    public void perform_Http_Request(String httpMethod, String url) throws Throwable {
        facade.doRequest(httpMethod, url);
    }
}
