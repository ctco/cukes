package lv.ctco.cukes.core.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

@Singleton
public class GivenSteps {

    @Inject
    VariableFacade variableFacade;

    @Inject
    GlobalWorldFacade world;

    @Given("^let variable \"(.+)\" to be random UUID$")
    public void var_random_UUID(String varName) {
        this.variableFacade.setUUIDToVariable(varName);
    }

    @Given("^let variable \"(.+)\" equal to \"(.+)\"$")
    public void var_assigned(String varName, String value) {
        this.variableFacade.setVariable(varName, value);
    }

    @Given("^let variable \"([^\"]*)\" be equal to current timestamp$")
    public void letVariableBeEqualToCurrentTimestamp(String varName) {
        this.variableFacade.setCurrentTimestampToVariable(varName);
    }

    @Given("^value assertions are case-insensitive$")
    public void val_caseInsensitive() {
        this.world.put("case-insensitive", "true");
    }

    @Given("^resources root is \"(.+)\"$")
    public void resources_Root_Is(String url) {
        var_assigned(CukesOptions.RESOURCES_ROOT, url);
    }
}
