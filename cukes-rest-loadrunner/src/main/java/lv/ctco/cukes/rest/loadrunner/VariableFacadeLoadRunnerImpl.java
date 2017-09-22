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
        loadRunnerFilter.getTrx().addFunction(() -> "lr_set_string(\"" + name + "\", " + wrapIfNeeded(value) + ");\n");
    }

    private String wrapIfNeeded(final String value) {
        return isFunctionCall(value) ? value : "\"" + value + "\"";
    }

    private boolean isFunctionCall(String value) {
        return value.matches("\\S+\\([\\W\\S,]*\\)");
    }

    @Override
    public void setUUIDToVariable(final String name) {
        loadRunnerFilter.getTrx().addFunction(() -> "lr_save_string(lr_guid_gen(), \"" + name + "\");\n");
    }

    @Override
    public void setCurrentTimestampToVariable(final String name) {
        loadRunnerFilter.getTrx().addFunction(() -> "lr_save_timestamp(\"" + name + "\", \"DIGITS=10\", LAST);");
    }
}
