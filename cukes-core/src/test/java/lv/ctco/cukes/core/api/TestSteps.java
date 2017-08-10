package lv.ctco.cukes.core.api;

import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lv.ctco.cukes.core.internal.context.InflateContext;

@Singleton
@InflateContext
public class TestSteps {

    @Then("^variable \"(.+)\" is set to \"(.+)\"$")
    public void variableIsSet(String variableName, String variableValue) {
        System.out.println(variableName + "=" + variableValue);
    }
}
