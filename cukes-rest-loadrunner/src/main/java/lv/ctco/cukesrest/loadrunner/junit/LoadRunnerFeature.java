package lv.ctco.cukesrest.loadrunner.junit;

import com.jayway.restassured.*;
import com.jayway.restassured.filter.Filter;
import cucumber.runtime.Runtime;
import cucumber.runtime.junit.*;
import cucumber.runtime.model.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.loadrunner.*;
import org.junit.runner.notification.*;
import org.junit.runners.model.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

class LoadRunnerFeature extends FeatureRunner {

    public static final String LOADRUNNER_OUTPUT_DIR = "target/loadrunner_output";

    private final Logger logger = Logger.getLogger(LoadRunnerFeature.class.getName());

    private final LoadRunnerFilter filter;
    private final CucumberFeature cucumberFeature;

    public LoadRunnerFeature(CucumberFeature cucumberFeature, Runtime runtime, JUnitReporter jUnitReporter,
                             LoadRunnerFilter filter) throws InitializationError {
        super(cucumberFeature, runtime, jUnitReporter);
        this.filter = filter;
        this.cucumberFeature = cucumberFeature;
    }

    @Override
    public void run(RunNotifier notifier) {
        RestAssured.filters(filter);
        filter.createLoadRunnerAction();
        super.run(notifier);

        try {
            File dir = new File(LOADRUNNER_OUTPUT_DIR);
            if (!dir.exists()) {
                boolean mkdirsFailed = !dir.mkdirs();
                if (mkdirsFailed) throw new CukesRuntimeException("Failed to create Folder: " + LOADRUNNER_OUTPUT_DIR);
            }

            String fileName = createName(extractFeatureName()) + ".c";
            File file = new File(LOADRUNNER_OUTPUT_DIR + File.separator + fileName);
            OutputStream out = new FileOutputStream(file);
            filter.dump(out);
            out.close();

            logger.info(file.getAbsolutePath());

            ArrayList<Filter> filtersCopy = new ArrayList<Filter>(RestAssured.filters());
            filtersCopy.remove(filter);
            RestAssured.replaceFiltersWith(filtersCopy);
        } catch (Exception e) {
            throw new CukesRuntimeException(e);
        }
    }

    private String extractFeatureName() {
        return cucumberFeature.getGherkinFeature().getName();
    }

    public String createName(String featureName){
        String newName = featureName.replace(" ", "_");
        return newName;
    }
}
