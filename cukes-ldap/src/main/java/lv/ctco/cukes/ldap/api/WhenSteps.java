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

    @When("^the client creates entity using LDIF:$")
    public void createEntityFromLdif(String ldif) {
        entityFacade.importLdif(ldif);
    }

    @When("^the client creates entity using LDIF from file \"(.+)\"$")
    public void createEntityFromLdifFile(String ldifFile) {
        entityFacade.importLdifFromFile(ldifFile);
    }

    @When("^the client deletes entity with DN \"(.+)\"$")
    public void deleteEntityWithDn(String dn) {
        entityFacade.deleteEntityByDn(dn);
    }
}
