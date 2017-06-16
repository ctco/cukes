package lv.ctco.cukes.rest.loadrunner;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.rest.loadrunner.function.LoadRunnerFunction;
import lv.ctco.cukes.rest.facade.RestAssertionFacade;

@Singleton
@InflateContext
public class RestAssertionFacadeLoadRunnerImpl implements RestAssertionFacade {

    @Inject
    LoadRunnerFilter loadRunnerFilter;

    @Override
    public void bodyEqualTo(String body) {

    }

    @Override
    public void bodyNotEqualTo(String body) {

    }

    @Override
    public void bodyNotEmpty() {

    }

    @Override
    public void bodyContains(String body) {

    }

    @Override
    public void bodyDoesNotContain(String body) {

    }

    @Override
    public void headerIsEmpty(String headerName) {

    }

    @Override
    public void headerIsNotEmpty(String headerName) {

    }

    @Override
    public void statusCodeIs(final int statusCode) {
        this.loadRunnerFilter.getTrx().addFunction(new LoadRunnerFunction() {
            @Override
            public String format() {
                return "HttpRetCode = atoi(lr_eval_string(\"{httpcode}\"));\n\n" + "if (HttpRetCode == " + statusCode + "){\n"
                        + "lr_log_message(\"Request response code is as expected\");\n" + "} else { \n" + " transactionStatus = LR_FAIL;\n"
                        + " actionStatus = LR_FAIL;\n" + "}\n\n";
            }
        });
    }

    @Override
    public void statusCodeIsNot(int statusCode) {

    }

    @Override
    public void headerEndsWith(String headerName, String suffix) {

    }

    @Override
    public void varAssignedFromHeader(final String varName, final String headerName) {
        this.loadRunnerFilter.getTrx().addFunction(new LoadRunnerFunction() {
            @Override
            public String format() {
                return "SaveBoundedValue(\"" + varName + "\", lr_eval_string(\"{ResponseHeaders}\"), \"" + headerName + ": \", \"\\r\\n\");\n";
            }
        });
    }

    @Override
    public void headerEqualTo(String headerName, String value) {

    }

    @Override
    public void headerNotEqualTo(String headerName, String value) {

    }

    @Override
    public void headerContains(String headerName, String text) {

    }

    @Override
    public void headerDoesNotContain(String headerName, String text) {

    }

    @Override
    public void bodyContainsPropertiesFromJson(String json) {

    }

    @Override
    public void bodyContainsPathWithValue(String path, String value) {

    }

    @Override
    public void bodyContainsPathWithOtherValue(String path, String value) {

    }

    @Override
    public void bodyDoesNotContainPath(String path) {

    }

    @Override
    public void bodyContainsArrayWithSize(String path, String size) {

    }

    @Override
    public void bodyContainsPathOfType(String path, String type) {

    }

    @Override
    public void bodyContainsPathMatchingPattern(String path, String pattern) {

    }

    @Override
    public void bodyContainsPathNotMatchingPattern(String path, String pattern) {

    }

    @Override
    public void varAssignedFromProperty(final String varName, final String property) {
        this.loadRunnerFilter.getTrx().addFunction(new LoadRunnerFunction() {
            @Override
            public String format() {
                return "web_js_run(\n" +
                    "\t\"Code=jsonBody()." + property + ";\", \n" +
                    "\t\"ResultParam=" + varName + "\",\n" +
                    "\tSOURCES,\n" +
                    "\t\"Code=var jsonBody = function() {return JSON.parse(LR.getParam('ResponseBody'))}\",\n" +
                    "\tENDITEM,\n" +
                    "\tLAST\n" +
                    ");\n";
            }
        });
    }

    @Override
    public void varAssignedFromBody(final String varName) {
        this.loadRunnerFilter.getTrx().addFunction(new LoadRunnerFunction() {
            @Override
            public String format() {
                return "lr_set_string(\"" + varName + "\", lr_eval_string(\"{ResponseBody}\"));\n";
            }
        });
    }

    @Override
    public void bodyContainsJsonPathValueContainingPhrase(String path, String phrase) {
    }

    @Override
    public void failureOccurs(String exceptionClass) {
    }

    @Override
    public void failureIsExpected() {
    }

    @Override
    public void bodyContainsArrayWithEntryHavingValue(String path, String value) {
    }
}
