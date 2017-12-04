package lv.ctco.cukes.core.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.And;
import lv.ctco.cukes.core.CukesDocs;
import lv.ctco.cukes.core.facade.RandomGeneratorFacade;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;

import java.text.SimpleDateFormat;

@Singleton
@InflateContext
public class CoreSteps {

    @Inject
    private VariableFacade variableFacade;
    @Inject
    private RandomGeneratorFacade randomGeneratorFacade;

    @And("^variable \"([^\"]+)\" is \"([^\"]+)\"$")
    @CukesDocs("Assigns value to the variable")
    public void createVariableSetToValue(String varName, String value) {
        this.variableFacade.setVariable(varName, value);
    }

    @And("^variable \"([^\"]+)\" is random UUID$")
    @CukesDocs("Generates random UUID and assigns it to a variable")
    public void createVariableSetToRandomUUID(String varName) {
        this.variableFacade.setUUIDToVariable(varName);
    }

    @And("^variable \"(\\S+)\" is random password by matching pattern \"([Aa0]+)\"$")
    @CukesDocs("Generates random password by given pattern. Pattern may contain symbils a,A,0. " +
        "So A is replaced with random capital letter, a - with random letter and 0 - with random number")
    public void createVariableSetToRandomPasswordByPattern(String variableName, String pattern) {
        this.variableFacade.setVariable(variableName, this.randomGeneratorFacade.byPattern(pattern));
    }

    @And("^variable \"(\\S+)\" is random password with length \\d+$")
    @CukesDocs("Generates random password with given length")
    public void createVariableSetToRandomPasswordByLength(String variableName, int length) {
        this.variableFacade.setVariable(variableName, this.randomGeneratorFacade.withLength(length));
    }

    @And("^variable \"([^\"]+)\" is current timestamp$")
    @CukesDocs("Assigns current timestamp to a variable")
    public void createVariableSetToCurrentTimestamp(String varName) {
        this.variableFacade.setCurrentTimestampToVariable(varName);
    }

    @And("^variable \"([^\"]+)\" is current time in format \"([^\"]+)\"$")
    @CukesDocs("Assigns current timestamp in a defined format to a variable")
    public void createVariableSetToTodayDate(String varName, String format) {
        String value = new SimpleDateFormat(format).format(System.currentTimeMillis());
        this.variableFacade.setVariable(varName, value);
    }

    @And("^variable \"([^\"]+)\" is \"([^\"]*)\" with (\\d+) random characters?$")
    @CukesDocs("Generates random character line of required length and adds it to a variable value")
    public void createVariableSetToValueWithRandomStringPostfix(String varName, String value, int length) {
        value += this.randomGeneratorFacade.withLength(length);
        this.variableFacade.setVariable(varName, value);
    }

    @And("^variable \"([^\"]+)\" is \"([^\"]*)\" set to lower case$")
    @CukesDocs("Assigns value set to lower case to the variable")
    public void createVariableSetToLowerCase(String varName, String value) {
        this.variableFacade.setVariable(varName, value.toLowerCase());
    }

    @And("^variable \"([^\"]+)\" is \"([^\"]*)\" set to upper case$")
    @CukesDocs("Assigns value set to upper case to the variable")
    public void createVariableSetToUpperCase(String varName, String value) {
        this.variableFacade.setVariable(varName, value.toUpperCase());
    }
}
