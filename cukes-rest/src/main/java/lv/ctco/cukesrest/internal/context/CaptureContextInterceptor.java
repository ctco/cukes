package lv.ctco.cukesrest.internal.context;

import com.google.inject.*;
import org.aopalliance.intercept.*;

import java.lang.annotation.*;

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
