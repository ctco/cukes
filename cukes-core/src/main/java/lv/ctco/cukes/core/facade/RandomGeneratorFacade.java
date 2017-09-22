package lv.ctco.cukes.core.facade;

public interface RandomGeneratorFacade {
    String byPattern(String pattern);

    String withLength(int length);
}
