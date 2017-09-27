package lv.ctco.cukes.ldap.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.When;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.ldap.facade.EntityFacade;
import lv.ctco.cukes.ldap.facade.ModificationFacade;

@Singleton
@InflateContext
public class WhenSteps {

    @Inject
    EntityFacade entityFacade;
    @Inject
    ModificationFacade modificationFacade;

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

    @When("^the client updates entity with DN \"(.+)\" using prepared modifications$")
    public void modifyEntityWithDn(String dn) {
        modificationFacade.execute(dn);
    }

    @When("^the client searches entities within DN \"(.+)\" by filter \"(.+)\"$")
    public void clientSearchEntitiesByFilter(String dn, String filter) {
        entityFacade.searchByFilter(dn, filter);
    }
}
