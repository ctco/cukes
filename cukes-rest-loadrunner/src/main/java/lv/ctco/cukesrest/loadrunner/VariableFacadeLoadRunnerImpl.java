package lv.ctco.cukesrest.loadrunner;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukesrest.internal.VariableFacade;
import lv.ctco.cukesrest.internal.context.InflateContext;
import lv.ctco.cukesrest.loadrunner.function.LoadRunnerFunction;

@Singleton
@InflateContext
public class VariableFacadeLoadRunnerImpl implements VariableFacade {

    @Inject
    LoadRunnerFilter loadRunnerFilter;

    @Override
    public void setVariable(final String name, final String value) {
        loadRunnerFilter.getTrx().addFunction(new LoadRunnerFunction() {
            @Override
            public String format() {
                return "lr_set_string(\"" + name + "\", \"" + value + "\");\n";
            }
        });
    }

    @Override
    public void setUUIDToVariable(final String name) {
        loadRunnerFilter.getTrx().addFunction(new LoadRunnerFunction() {
            @Override
            public String format() {
                return "random_Generator(\"" + name + "\", 32);\n";
            }
        });
    }
}
