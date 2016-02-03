package lv.ctco.cukesrest;

public interface CukesRestPlugin {

    void beforeAllTests();

    void afterAllTests();

    void beforeScenario();

    void afterScenario();

    void beforeRequest();

    void afterRequest();
}
