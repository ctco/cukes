package lv.ctco.cukes.core.di;

import io.cucumber.guice.ScenarioScoped;
import lv.ctco.cukes.core.internal.di.SingletonObjectFactory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SingletonObjectFactoryTests {

    private static SingletonObjectFactory instance;

    @BeforeClass
    public static void setup() {
        instance = SingletonObjectFactory.instance();
        instance.addModule(binder -> binder.bind(ScenarioScopedClass.class).in(ScenarioScoped.class));
    }

    @After
    public void tearDown() {
        try {
            simulateCucumberScenarioStop();
        } catch (Exception ignored) {
        }
    }

    private void simulateCucumberScenarioStop() {
        instance.stop();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenAlreadyUsed() {
        // simulate cucumber scenario start
        simulateCucumberScenarioStart();

        instance.addModule(binder -> {
        });
    }

    private void simulateCucumberScenarioStart() {
        instance.start();
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

    public static class ScenarioScopedClass {
        public int value = 0;
    }
}
