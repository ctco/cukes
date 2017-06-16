package lv.ctco.cukes.rest.loadrunner.function;

import lv.ctco.cukes.core.internal.helpers.Strings;

import java.util.ArrayList;
import java.util.List;

public class WebCustomRequest implements LoadRunnerFunction {
    private String name;
    private String url;
    private String method;
    private String resource;
    private String mode;
    private String body;
    private String snapshot;
    private List<LoadRunnerFunction> beforeFunctions = new ArrayList<LoadRunnerFunction>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public List<LoadRunnerFunction> getBeforeFunctions() {
        return beforeFunctions;
    }

    public void setBeforeFunctions(List<LoadRunnerFunction> beforeFunctions) {
        this.beforeFunctions = beforeFunctions;
    }

    public String format() {
        StringBuilder result = new StringBuilder();
        for (LoadRunnerFunction function : beforeFunctions) {
            result.append(function.format());
        }
        if (beforeFunctions.size() > 0) result.append("\n");

        result.append("web_custom_request(\"").append(name).append("\",\n");

        appendUrl(url, result);
        appendOptionalParameter("Method", method, result);
        appendOptionalParameter("Resource", resource, result);
        appendOptionalParameter("Mode", mode, result);
        appendOptionalParameter("Body", body, result);
        appendOptionalParameter("Snapshot", snapshot, result);

        return result.append(" LAST);\n\n").toString();
    }

    private void appendUrl(String url, StringBuilder stringBuilder) {
        if (!Strings.isEmpty(url))
            stringBuilder.append(" concat(\"URL=\",getUrl(\"")
                .append(url.replace("\"", "\\\""))
                .append("\")),\n");

    }

    private void appendOptionalParameter(String title, String value, StringBuilder stringBuilder) {
        if (!Strings.isEmpty(value))
            stringBuilder.append(" \"")
                .append(title)
                .append("=")
                .append(value.replace("\"", "\\\"").replace("\r\n", "").replace("\n", ""))
                .append("\",\n");
    }
}
