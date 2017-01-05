package lv.ctco.cukesrest.formatter;

import cucumber.runtime.formatter.*;
import gherkin.formatter.*;
import gherkin.formatter.model.*;
import lv.ctco.cukesrest.di.SingletonObjectFactory;
import lv.ctco.cukesrest.internal.context.*;

import java.lang.reflect.*;
import java.util.*;

public class CukesRestJsonFormatter extends CucumberJSONFormatter {

    Method getSteps;
    ContextInflater contextInflater;

    public CukesRestJsonFormatter(Appendable out) throws Exception {
        super(out);
        contextInflater = SingletonObjectFactory.instance().getInstance(ContextInflater.class);
        getSteps = JSONFormatter.class.getDeclaredMethod("getSteps");
        getSteps.setAccessible(true);
    }

    @Override
    public void match(Match match) {
        List<Argument> inflatedArguments = new ArrayList<Argument>();
        for (Argument argument : match.getArguments()) {
            String inflatedVal = contextInflater.inflate(argument.getVal());
            inflatedArguments.add(new Argument(argument.getOffset(), inflatedVal));
        }
        super.match(new Match(inflatedArguments, match.getLocation()));
        Map<String, Object> currentStep = getCurrentStep("match");
        String name = ((String) currentStep.get("name"));
        String inflatedName = contextInflater.inflate(name);
        currentStep.put("name", inflatedName);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getCurrentStep(String target) {
        try {
            Map lastWithValue = null;
            List<Map> invoke = ((List<Map>) getSteps.invoke(this));
            for (Map stepOrHook : invoke) {
                if (stepOrHook.get(target) == null) {
                    return stepOrHook;
                } else {
                    lastWithValue = stepOrHook;
                }
            }
            return lastWithValue;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
