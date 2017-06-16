package lv.ctco.cukes.core.internal.switches;

import com.google.inject.Inject;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwitchedByInterceptor implements MethodInterceptor {

    @Inject
    GlobalWorldFacade world;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Annotation[] annotations = invocation.getMethod().getAnnotations();

        SwitchedBy annotation = getAnnotation(annotations, SwitchedBy.class);
        if (annotation == null) {
            annotations = getCurrentAndSuperclassAnnotations(invocation.getThis().getClass());
            annotation = getAnnotation(annotations, SwitchedBy.class);
        }

        if (annotation != null) {
            String switchedByKey = annotation.value();
            boolean switchedBy = world.getBoolean(switchedByKey);
            if (switchedBy) {
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
