package lv.ctco.cukes.graphql.internal;

import java.util.HashMap;
import java.util.Map;

public class GraphQLRequest {

    private String operationName;
    private String query;
    private Map<String, String> variables = new HashMap<String, String>();

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
}
