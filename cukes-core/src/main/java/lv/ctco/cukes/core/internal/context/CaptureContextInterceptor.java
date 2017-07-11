package lv.ctco.cukes.core.internal.context;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

public class CaptureContextInterceptor implements MethodInterceptor {

    @Inject
    ContextCapturer capturer;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();

        Object pattern = null;
        Object value = null;

        Annotation[][] annotations = invocation.getMethod().getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Annotation[] parameterAnnotations = annotations[i];
            for (Annotation annotation : parameterAnnotations) {
                if (annotation.annotationType().equals(CaptureContext.Pattern.class)) {
                    pattern = arguments[i];
                } else if (annotation.annotationType().equals(CaptureContext.Value.class)) {
                    value = arguments[i];
                }
            }
        }

        if (pattern != null && value != null) {
            capturer.capture((String) pattern, (String) value);
        }
        return invocation.proceed();
    }
}
