package lv.ctco.cukesrest.internal;

public interface VariableFacade {

    String VARIABLE_FACADE = "VariableFacade";

    void setVariable(String name, String value);

    void setUUIDToVariable(String name);

    void setCurrentTimestampToVariable(String name);

}
