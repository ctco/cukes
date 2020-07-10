package lv.ctco.cukes.core.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.util.Set;

@Singleton
public class CucumberFacade {

    /* Ugly Hack proposed by Cucumber developers: https://github.com/cucumber/cucumber-jvm/pull/295 */
    private static boolean firstRun = true;

    @Inject
    GlobalWorldFacade world;

    @Inject
    Set<CukesPlugin> pluginSet;


    public boolean firstScenario() {
        return firstRun;
    }

    public void beforeAllTests() {
        firstRun = false;
        for (CukesPlugin cukesPlugin : pluginSet) {
            cukesPlugin.beforeAllTests();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> afterAllTests()));
    }

    public void beforeScenario() {
        world.reconstruct();
        for (CukesPlugin cukesPlugin : pluginSet) {
            cukesPlugin.beforeScenario();
        }
    }

    public void afterScenario() {
        for (CukesPlugin cukesPlugin : pluginSet) {
            cukesPlugin.afterScenario();
        }
    }

    public void afterAllTests() {
        for (CukesPlugin cukesPlugin : pluginSet) {
            cukesPlugin.afterAllTests();
        }
    }
}
