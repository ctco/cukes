package lv.ctco.cukes.core.internal.context;

import lv.ctco.cukes.core.CukesRuntimeException;

public class CukesMissingPropertyException extends CukesRuntimeException {
    public CukesMissingPropertyException(String key) {
        super("Property cukes." + key + " is missing");
    }

    public CukesMissingPropertyException(String key, String additionalInfo) {
        super("Property cukes." + key + " is missing: " + additionalInfo);
    }
}
