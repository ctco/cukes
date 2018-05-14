package lv.ctco.cukes.sql.steps;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.sql.facade.DataBaseRequestFacade;

import java.util.List;
import java.util.Map;

@Singleton
@InflateContext
@Slf4j
public class DataBaseSteps {

    @Inject
    private DataBaseRequestFacade facade;

    private static final String TABLE_NAME_OR_VAR = "(?:([\\w]+|\\{\\([^)}]+\\)})(?:\\.))?([\\w]+|\\{\\([^)}]+\\)})";
    private static final String NUMBER_OR_VAR = "(\\d+||\\{\\([^)}]+\\)})";

    @Then("^DB table " + TABLE_NAME_OR_VAR + " should contain:$")
    public void checkSchemeTableContains(String scheme, String tableName, List<Map<String, String>> tableValues) {
        facade.checkSchemeTableContains(scheme, tableName, tableValues);
    }

    @Then("^DB table " + TABLE_NAME_OR_VAR + " should match:$")
    public void checkSchemeTableMatch(String scheme, String tableName, List<Map<String, String>> tableValues) {
        facade.checkSchemeTableMatch(scheme, tableName, tableValues);
    }

    @Then("^DB table " + TABLE_NAME_OR_VAR + " row count should be (<|<=|=|!=|>=|>) " + NUMBER_OR_VAR + "$")
    public void checkTableRowCount(String schema, String tableName, String sign, Integer number) {
        facade.checkSchemeTableCount(schema, tableName, sign, number);
    }

    @Then("^DB table " + TABLE_NAME_OR_VAR + " row count should (not be|be) empty$")
    public void checkTableIsEmpty2(String schema, String tableName, String statement) {
        facade.checkSchemeTableCount(schema, tableName, statement.equals("be") ? "=" : "!=", 0);
    }
}
