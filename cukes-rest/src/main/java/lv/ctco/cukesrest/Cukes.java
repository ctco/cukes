package lv.ctco.cukesrest;

import lv.ctco.cukesrest.internal.CukesConfig;

/**
 * Entry point to Cukes Configuration
 */
public abstract class Cukes {

    public static CukesConfig config() {
        return new CukesConfig();
    }
}
