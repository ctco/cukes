package lv.ctco.cukes.core.extension;


public interface CukesPlugin {

    void beforeAllTests();

    void afterAllTests();

    void beforeScenario();

    void afterScenario();

}
