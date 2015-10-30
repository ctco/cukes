package lv.ctco.cukesrest.internal.switches;

import com.google.inject.Inject;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwitchedByInterceptor implements MethodInterceptor {

    @Inject
    GlobalWorldFacade facade;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Annotation[] annotations = invocation.getMethod().getAnnotations();

        SwitchedBy annotation = getAnnotation(annotations, SwitchedBy.class);
        if(annotation == null) {
            annotations = getCurrentAndSuperclassAnnotations(invocation.getThis().getClass());
            annotation = getAnnotation(annotations, SwitchedBy.class);
        }

        if (annotation != null) {
            String switchedBy = annotation.value();
            if (facade.getBoolean(switchedBy)) {
                return null;
            }
        }
        return invocation.proceed();
    }

    private Annotation[] getCurrentAndSuperclassAnnotations(Class<? extends Object> clazz) {
        List<Annotation> annotations = new ArrayList<Annotation>(Arrays.asList(clazz.getAnnotations()));
        for (Class superclazz = clazz.getSuperclass(); superclazz != null; superclazz = superclazz.getSuperclass()){
            annotations.addAll(Arrays.asList(superclazz.getAnnotations()));
        }
        return annotations.toArray(new Annotation[annotations.size()]);
    }

    private <T> T getAnnotation(Annotation[] annotations, Class<T> clazz) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(clazz)) {
                return (T) annotation;
            }
        }
        return null;
    }
}
