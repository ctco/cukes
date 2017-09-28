package lv.ctco.cukes.graphql.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import lv.ctco.cukes.http.facade.HttpRequestFacade;
import lv.ctco.cukes.http.facade.HttpResponseFacade;

@Singleton
public class WhenSteps {

    @Inject
    private HttpRequestFacade requestFacade;

    @Inject
    private HttpResponseFacade responseFacade;

    @When("^the query is executed$")
    public void execute_Query() throws Throwable {
        String contentType = ContentType.JSON.toString();
        requestFacade.accept(contentType);
        requestFacade.contentType(contentType);
        responseFacade.setResponsePrefix("data.");
        responseFacade.doRequest("POST", "");
    }
}
