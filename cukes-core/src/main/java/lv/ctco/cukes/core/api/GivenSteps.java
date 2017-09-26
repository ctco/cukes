package lv.ctco.cukes.core.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.core.CukesDocs;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

@Singleton
public class GivenSteps {

    @Inject
    VariableFacade variableFacade;
    @Inject
    RandomGeneratorFacade randomGeneratorFacade;

    @Inject
    GlobalWorldFacade world;

    @Given("^let variable \"(.+)\" to be random UUID$")
    @CukesDocs("Generates random UUID and assigns it to a variable")
    public void var_random_UUID(String varName) {
        this.variableFacade.setUUIDToVariable(varName);
    }

    @Given("^let variable \"(\\S+)\" to be random password by matching pattern \"([Aa0]+)\"$")
    @CukesDocs("Generates random password by given pattern. Pattern may contain symbils a,A,0. " +
        "So A is replaced with random capital letter, a - with random letter and 0 - with random number")
    public void var_random_password_by_pattern(String variableName, String pattern) {
        this.variableFacade.setVariable(variableName, this.randomGeneratorFacade.byPattern(pattern));
    }

    @Given("^let variable \"(\\S+)\" to be random password with length \\d+$")
    @CukesDocs("Generates random password with given length")
    public void var_randomPassword_by_length(String variableName, int length) {
        this.variableFacade.setVariable(variableName, this.randomGeneratorFacade.withLength(length));
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
