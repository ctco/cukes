package lv.ctco.cukes.ldap.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.ldap.facade.EntityFacade;

@Singleton
@InflateContext
public class WhenSteps {

    @Inject
    EntityFacade entityFacade;

    @When("^the client retrieves entity by DN \"(.+)\"$")
    public void readEntityByDn(String dn) {
        entityFacade.readEntityByDn(dn);
    }

    @When("^the client creates entity with DN \"(.+)\"$")
    public void createEntityWithDn(String dn) {
        throw new CukesRuntimeException("Not implemented");
    }

    @When("^the client deletes entity with DN \"(.+)\"$")
    public void deleteEntityWithDn(String dn) {
        throw new CukesRuntimeException("Not implemented");
    }
}
