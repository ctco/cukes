package lv.ctco.cukes.core.extension;

import com.google.inject.Inject;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;

import java.util.Map;

public class EnvironmentVariablesPlugin implements CukesPlugin {
    public static final String CUKES_PREFIX = "CUKES_";
    @Inject
    GlobalWorldFacade world;

    @Override
    public void beforeAllTests() {
        // Do nothing
    }

    @Override
    public void afterAllTests() {
        // Do nothing
    }

    @Override
    public void beforeScenario() {
        Map<String, String> envVars = System.getenv();
        envVars.entrySet().stream().
            filter(ev -> ev.getKey().toUpperCase().startsWith("CUKES_")).
            forEach(ev -> world.put(
                ev.getKey().substring(CUKES_PREFIX.length()),
                ev.getValue()
            ));
    }

    @Override
    public void afterScenario() {
        // Do nothing
    }
}
