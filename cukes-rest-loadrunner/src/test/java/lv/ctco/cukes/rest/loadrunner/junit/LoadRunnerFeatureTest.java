package lv.ctco.cukes.rest.loadrunner.junit;


import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;
import gherkin.ast.Feature;
import gherkin.ast.GherkinDocument;
import lv.ctco.cukes.rest.loadrunner.LoadRunnerFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;


public class LoadRunnerFeatureTest {

    @Mock cucumber.runtime.Runtime runtime;
    @Mock CucumberFeature cukesFeature;
    @Mock JUnitReporter reporter;
    @Mock LoadRunnerFilter filter;

    @Mock GherkinDocument gherkinDocument;
    @Mock Feature feature;

    LoadRunnerFeature loadRunnerFeature;

    @Before
    public void setupMocks() throws InitializationError  {
        MockitoAnnotations.initMocks(this);
        when(cukesFeature.getGherkinFeature()).thenReturn(gherkinDocument);
        when(gherkinDocument.getFeature()).thenReturn(feature);
        loadRunnerFeature = new LoadRunnerFeature(cukesFeature, runtime, reporter, filter);
    }

    @Test
    public void shouldCheckFileNameGeneration() throws Exception {
        String filename = "My feature";
        String refactoredName = loadRunnerFeature.createName(filename);
        assertThat(refactoredName, is("My_feature"));
    }

}
