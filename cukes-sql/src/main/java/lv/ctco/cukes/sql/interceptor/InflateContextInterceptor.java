package lv.ctco.cukes.sql.interceptor;

import com.google.inject.Inject;
import lv.ctco.cukes.core.internal.context.ContextInflater;
import lv.ctco.cukes.core.internal.context.InflateContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InflateContextInterceptor implements MethodInterceptor {
	@Inject
	private ContextInflater inflater;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] arguments = invocation.getArguments();
		Annotation[][] annotations = invocation.getMethod().getParameterAnnotations();

		for (int i = 0; i < annotations.length; i++) {
			if (!toIgnore(annotations[i])) {
				arguments[i] = inflate(arguments[i]);
			}
		}
		return invocation.proceed();
	}

	private boolean toIgnore(Annotation[] parameterAnnotations) {
		return Arrays.stream(parameterAnnotations)
				.map(Annotation::annotationType)
				.anyMatch(InflateContext.Ignore.class::equals);
	}

	//TODO add other Collection type checks
	//TODO create object of expected subtype
	private Object inflate(Object argument) {
		if (argument instanceof List) {
			List list = ((List) argument);
			return list.stream().map(this::inflate).collect(Collectors.toList());
		}

		if (argument instanceof Map) {
			Map map = ((Map) argument);
			Map<Object, Object> newMap = new HashMap<>();
			map.forEach((key, value) -> newMap.put(key, inflate(value)));
			return newMap;
		}

		if (argument instanceof String) {
			return inflater.inflate(argument.toString());
		}
		return argument;
	}
}
