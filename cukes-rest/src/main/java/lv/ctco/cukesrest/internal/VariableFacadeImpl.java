package lv.ctco.cukesrest.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import lv.ctco.cukesrest.internal.context.InflateContext;

import java.util.UUID;

@Singleton
@InflateContext
public class VariableFacadeImpl implements VariableFacade {

    @Inject
    private GlobalWorldFacade world;

    @Override
    public void setVariable(String name, String value) {
        world.put(name, value);
    }

    @Override
    public void setUUIDToVariable(String name) {
        world.put(name, UUID.randomUUID().toString());
    }
}
