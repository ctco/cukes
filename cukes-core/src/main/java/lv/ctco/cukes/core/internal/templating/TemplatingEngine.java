package lv.ctco.cukes.core.internal.templating;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import org.rythmengine.Rythm;

import java.util.HashMap;
import java.util.Map;

@Singleton
@InflateContext
public class TemplatingEngine {

    @Inject
    GlobalWorldFacade world;

    private Boolean isBodyTemplatesEnabled() {
        return world.getBoolean(CukesOptions.REQUEST_BODY_TEMPLATES_ENABLED);
    }

    public String processBody(String body) {
        if (isBodyTemplatesEnabled()) {
            final Map<String, String> rythmParams = new HashMap<String, String>();
            for (String key : world.keys()) {
                Optional<String> value = world.get(key);
                if (value.isPresent() && body.contains("@" + key)) rythmParams.put(key, value.get());
            }
            return Rythm.render(body, rythmParams);
        }
        return body;
    }
}
