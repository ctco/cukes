package lv.ctco.cukesrest.internal;

import com.google.inject.*;
import lv.ctco.cukesrest.*;

import java.util.*;

@Singleton
public class CucumberFacade {

    /* Ugly Hack proposed by Cucumber developers: https://github.com/cucumber/cucumber-jvm/pull/295 */
    private static boolean firstRun = true;

    @Inject
    Set<CukesRestPlugin> pluginSet;

    @Inject
    RequestSpecificationFacade requestSpecificationFacade;

    public boolean firstScenario() {
        return firstRun;
    }

    public void beforeAllTests() {
        firstRun = false;
        for (CukesRestPlugin cukesRestPlugin : pluginSet) {
            cukesRestPlugin.beforeAllTests();
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                afterAllTests();
            }
        });
    }

    public void beforeScenario() {
        for (CukesRestPlugin cukesRestPlugin : pluginSet) {
            cukesRestPlugin.beforeScenario();
        }
    }

    public void afterScenario() {
        for (CukesRestPlugin cukesRestPlugin : pluginSet) {
            cukesRestPlugin.afterScenario();
        }
        requestSpecificationFacade.initNewSpecification();
    }

    public void afterAllTests() {
        for (CukesRestPlugin cukesRestPlugin : pluginSet) {
            cukesRestPlugin.afterAllTests();
        }
    }
}
