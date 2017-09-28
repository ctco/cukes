package lv.ctco.cukes.docgen;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lv.ctco.cukes.core.CukesDocs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.function.Function;

public enum StepType {


    given(Given.class, method -> method.getAnnotation(Given.class).value()),
    when(When.class, method -> method.getAnnotation(When.class).value()),
    then(Then.class, method -> method.getAnnotation(Then.class).value());

    public static final Comparator<StepType> comparator = Comparator.comparing(Enum::ordinal);

    private Class<? extends Annotation> annotation;
    private Function<Method, String> patternProvider;

    StepType(Class<? extends Annotation> annotation, Function<Method, String> patternProvider) {
        this.annotation = annotation;
        this.patternProvider = patternProvider;
    }

    public static StepType getTypeForMethod(Method method) {
        for (StepType stepType : values()) {
            if (method.getAnnotation(stepType.annotation) != null) {
                return stepType;
            }
        }
        return null;
    }

    public String getPattern(Method method) {
        return patternProvider.apply(method);
    }

    public String getDescription(Method method) {
        CukesDocs docs = method.getAnnotation(CukesDocs.class);

        return docs == null ? null : docs.value();
    }
}
