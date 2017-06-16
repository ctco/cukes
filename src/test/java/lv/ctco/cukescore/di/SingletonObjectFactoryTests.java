package lv.ctco.cukescore.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import cucumber.api.guice.CucumberScopes;
import lv.ctco.cukescore.internal.di.SingletonObjectFactory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SingletonObjectFactoryTests {

    private static SingletonObjectFactory instance;

    @BeforeClass
    public static void setup() throws Exception {
        instance = SingletonObjectFactory.instance();
        instance.addModule(new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(ScenarioScopedClass.class).in(CucumberScopes.SCENARIO);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        try {
            simulateCucumberScenarioStop();
        } catch (Exception ignored) {
        }
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenAlreadyUsed() throws Exception {
        // simulate cucumber scenario start
        simulateCucumberScenarioStart();

        instance.addModule(new Module() {
            @Override
            public void configure(Binder binder) {
            }
        });
    }

    @Test
    public void shouldSupportScenarioScope() {
        simulateCucumberScenarioStart();

        final ScenarioScopedClass a = instance.getInstance(ScenarioScopedClass.class);
        a.value = 10;
        assertEquals(10, a.value);

        final ScenarioScopedClass b = instance.getInstance(ScenarioScopedClass.class);
        assertEquals(10, b.value);

        simulateCucumberScenarioStop();
        simulateCucumberScenarioStart();

        final ScenarioScopedClass c = instance.getInstance(ScenarioScopedClass.class);
        assertEquals(0, c.value);

        simulateCucumberScenarioStop();
    }

    private void simulateCucumberScenarioStart() {
        instance.start();
    }

    private void simulateCucumberScenarioStop() {
        instance.stop();
    }

    public static class ScenarioScopedClass {
        public int value = 0;
    }
}
