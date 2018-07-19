package lv.ctco.cukes.oauth.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.core.CukesDocs;
import lv.ctco.cukes.http.facade.HttpRequestFacade;
import lv.ctco.cukes.oauth.facade.OAuthFacade;

@Singleton
public class GivenSteps {

    @Inject
    HttpRequestFacade httpRequestFacade;
    @Inject
    OAuthFacade oAuthFacade;

    @Given("^using OAuth$")
    @CukesDocs("The following scenario will use OAuth")
    public void usingOAuth() {
        httpRequestFacade.authenticationType("OAuth");
    }

    @Given("^using (.+) grant type$")
    @CukesDocs("Specify which grant type is used")
    public void usingGrantType(String grantType) {
        oAuthFacade.setGrantType(grantType);
    }

    @Given("^using \"(.+)\" scopes$")
    @CukesDocs("Specify for which scopes access token will be requested")
    public void usingScopes(String scopes) {
        oAuthFacade.setScopes(scopes);
    }
}
