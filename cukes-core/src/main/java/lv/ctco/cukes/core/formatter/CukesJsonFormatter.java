package lv.ctco.cukes.core.formatter;

import cucumber.runtime.formatter.CucumberJSONFormatter;
import gherkin.formatter.Argument;
import gherkin.formatter.JSONFormatter;
import gherkin.formatter.model.Match;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.di.SingletonObjectFactory;
import lv.ctco.cukes.core.internal.context.ContextInflater;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CukesJsonFormatter extends CucumberJSONFormatter {

    Method getSteps;
    ContextInflater contextInflater;

    public CukesJsonFormatter(Appendable out) throws Exception {
        super(out);
        contextInflater = SingletonObjectFactory.instance().getInstance(ContextInflater.class);
        getSteps = JSONFormatter.class.getDeclaredMethod("getSteps");
        getSteps.setAccessible(true);
    }

    @Override
    public void match(Match match) {
        List<Argument> inflatedArguments = new ArrayList<>();
        for (Argument argument : match.getArguments()) {
            String inflatedVal = contextInflater.inflate(argument.getVal());
            inflatedArguments.add(new Argument(argument.getOffset(), inflatedVal));
        }
        super.match(new Match(inflatedArguments, match.getLocation()));
        Map<String, Object> currentStep = getCurrentStep();
        if (currentStep != null) {
            String name = ((String) currentStep.get("name"));
            String inflatedName = contextInflater.inflate(name);
            currentStep.put("name", inflatedName);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getCurrentStep() {
        List<Map> invoke;
        try {
            invoke = new ArrayList<>((List<Map>) getSteps.invoke(this));
        } catch (Exception e) {
            throw new CukesRuntimeException(e);
        }
        Collections.reverse(invoke);
        for (Map stepOrHook : invoke) {
            if (stepOrHook.get("match") != null) {
                return stepOrHook;
            }
        }
        return null; //should not happen
    }
}
