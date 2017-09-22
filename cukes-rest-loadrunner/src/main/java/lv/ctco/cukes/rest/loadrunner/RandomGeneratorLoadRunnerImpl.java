package lv.ctco.cukes.rest.loadrunner;

import lv.ctco.cukes.core.facade.RandomGeneratorFacade;

public class RandomGeneratorLoadRunnerImpl implements RandomGeneratorFacade {
    @Override
    public String byPattern(String pattern) {
        return "generateRandomPassword(\"" + pattern + "\")";
    }

    @Override
    public String withLength(int length) {
        return "generateRandomPasswordN(" + length + ")";
    }
}
