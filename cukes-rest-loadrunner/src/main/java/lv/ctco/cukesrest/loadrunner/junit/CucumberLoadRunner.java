package lv.ctco.cukesrest.loadrunner.junit;

import cucumber.runtime.*;
import cucumber.runtime.Runtime;
import cucumber.runtime.io.*;
import cucumber.runtime.junit.*;
import cucumber.runtime.model.*;
import lv.ctco.cukesrest.di.SingletonObjectFactory;
import lv.ctco.cukesrest.internal.*;
import lv.ctco.cukesrest.loadrunner.*;
import org.junit.runner.*;
import org.junit.runner.notification.*;
import org.junit.runners.*;
import org.junit.runners.model.*;

import java.io.*;
import java.util.*;

import static lv.ctco.cukesrest.CukesOptions.*;

public class CucumberLoadRunner extends ParentRunner<FeatureRunner> {
    private final JUnitReporter jUnitReporter;
    private final List<FeatureRunner> children = new ArrayList<FeatureRunner>();
    private final cucumber.runtime.Runtime runtime;
    private final LoadRunnerFilter filter;

    /**
     * Constructor called by JUnit.
     *
     * @param clazz the class with the @RunWith annotation.
     * @throws java.io.IOException                         if there is a problem
     * @throws org.junit.runners.model.InitializationError if there is another problem
     */
    public CucumberLoadRunner(Class clazz) throws InitializationError, IOException {
        super(clazz);

        System.setProperty(cukesProperty(CONTEXT_INFLATING_ENABLED), "false");
        System.setProperty(cukesProperty(ASSERTIONS_DISABLED), "true");
        System.setProperty(cukesProperty(LOADRUNNER_FILTER_BLOCKS_REQUESTS), "true");
        System.setProperty(AssertionFacade.ASSERTION_FACADE, AssertionFacadeLoadRunnerImpl.class.getCanonicalName());

        filter = SingletonObjectFactory.instance().getInstance(LoadRunnerFilter.class);

        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);

        final List<CucumberFeature> cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader);
        jUnitReporter = new JUnitReporter(runtimeOptions.reporter(classLoader), runtimeOptions.formatter(classLoader)
            , runtimeOptions.isStrict());
        addChildren(cucumberFeatures);
    }

    /**
     * Create the Runtime. Can be overridden to customize the runtime or backend.
     *
     * @param resourceLoader used to load resources
     * @param classLoader    used to load classes
     * @param runtimeOptions configuration
     * @return a new runtime
     */
    protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader, RuntimeOptions
        runtimeOptions) {
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
    }

    @Override
    public List<FeatureRunner> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(FeatureRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(FeatureRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        jUnitReporter.done();
        jUnitReporter.close();
        runtime.printSummary();
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            children.add(new LoadRunnerFeature(cucumberFeature, runtime, jUnitReporter, filter));
        }
    }

    private static String cukesProperty(String propertyName) {
        return "cukes." + propertyName;
    }
}
