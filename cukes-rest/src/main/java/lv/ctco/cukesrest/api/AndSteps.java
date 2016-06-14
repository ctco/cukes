package lv.ctco.cukesrest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.And;
import lv.ctco.cukesrest.internal.AssertionFacade;

@Singleton
public class AndSteps {
    @Inject
    private AssertionFacade assertionFacade;

    @And("^it fails with ([^\"]+)$")
    public void it_fails(String exceptionClass) {
        assertionFacade.failureOccurs(exceptionClass);
    }

}
