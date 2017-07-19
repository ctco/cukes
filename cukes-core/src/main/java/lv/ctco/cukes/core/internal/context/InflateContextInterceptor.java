package lv.ctco.cukes.core.internal.context;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

public class InflateContextInterceptor implements MethodInterceptor {

    @Inject
    ContextInflater inflater;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        Annotation[][] annotations = invocation.getMethod().getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            Annotation[] parameterAnnotations = annotations[i];
            boolean ignore = false;
            for (Annotation annotation : parameterAnnotations) {
                if (annotation.annotationType().equals(InflateContext.Ignore.class)) {
                    ignore = true;
                }
            }
            if (!ignore && arguments[i] instanceof String) {
                arguments[i] = inflater.inflate((String) arguments[i]);
            }
        }
        return invocation.proceed();
    }
}
