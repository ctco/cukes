package lv.ctco.cukesrest.internal.switches;

import com.google.inject.*;
import lv.ctco.cukesrest.internal.context.*;
import org.aopalliance.intercept.*;

import java.lang.annotation.*;
import java.util.*;

public class SwitchedByInterceptor implements MethodInterceptor {

    @Inject
    GlobalWorldFacade facade;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Annotation[] annotations = invocation.getMethod().getAnnotations();

        SwitchedBy annotation = getAnnotation(annotations, SwitchedBy.class);
        if (annotation == null) {
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

    private Annotation[] getCurrentAndSuperclassAnnotations(Class<?> clazz) {
        List<Annotation> annotations = new ArrayList<Annotation>(Arrays.asList(clazz.getAnnotations()));
        for (Class superclass = clazz.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            annotations.addAll(Arrays.asList(superclass.getAnnotations()));
        }
        return annotations.toArray(new Annotation[annotations.size()]);
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> clazz) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(clazz)) {
                return (T) annotation;
            }
        }
        return null;
    }
}
