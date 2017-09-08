package lv.ctco.cukes.rest.loadrunner;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.facade.VariableFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;

@Singleton
@InflateContext
public class VariableFacadeLoadRunnerImpl implements VariableFacade {

    @Inject
    LoadRunnerFilter loadRunnerFilter;

    @Override
    public void setVariable(final String name, final String value) {
        loadRunnerFilter.getTrx().addFunction(() -> "lr_set_string(\"" + name + "\", \"" + value + "\");\n");
    }

    @Override
    public void setUUIDToVariable(final String name) {
        loadRunnerFilter.getTrx().addFunction(() -> "random_Generator(\"" + name + "\", 32);\n");
    }

    @Override
    public void setCurrentTimestampToVariable(final String name) {
        loadRunnerFilter.getTrx().addFunction(() -> "lr_save_timestamp(\"" + name + "\", \"DIGITS=10\", LAST);");
    }
}
