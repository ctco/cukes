package lv.ctco.cukes.ldap.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.ldap.facade.EntityFacade;
import lv.ctco.cukes.ldap.facade.SetupFacade;

@Singleton
@InflateContext
public class GivenSteps {

    @Inject
    SetupFacade setupFacade;
    @Inject
    EntityFacade entityFacade;

    @Given("^LDAP server URL is \"(.+)\"$")
    public void setUrl(String url) {
        setupFacade.setUrl(url);
    }

    @Given("^LDAP server can be connected by user with DN \"(.+)\" and password \"(.+)\"$")
    public void setUserDn(String userDn, String password) {
        setupFacade.setUserDn(userDn);
        setupFacade.setPassword(password);
    }

    @Given("^the client imports LDIF:$")
    public void importLdif(String ldif) {
        entityFacade.importLdif(ldif);
    }


}
