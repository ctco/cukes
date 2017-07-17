package lv.ctco.cukes.ldap;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.After;
import lv.ctco.cukes.ldap.facade.EntityFacade;
import lv.ctco.cukes.ldap.facade.SetupFacade;

@Singleton
public class CukesLDAPHooks {

    @Inject
    SetupFacade setupFacade;
    @Inject
    EntityFacade entityFacade;

    @After
    public void afterScenario() {
        setupFacade.initConfiguration();
        entityFacade.initConfiguration();
    }
}
