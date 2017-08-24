package lv.ctco.cukes.core.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;

import java.util.UUID;

@Singleton
@InflateContext
public class VariableFacadeImpl implements VariableFacade {

    @Inject
    private GlobalWorldFacade world;

    @Override
    public void setVariable(String name, String value) {
        if (value.equals("null")) {
            world.remove(name);
        }
        world.put(name, value);
    }

    @Override
    public void setUUIDToVariable(String name) {
        world.put(name, UUID.randomUUID().toString());
    }

    @Override
    public void setCurrentTimestampToVariable(String name) {
        world.put(name, String.valueOf(System.currentTimeMillis()));
    }
}
