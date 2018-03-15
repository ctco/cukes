package lv.ctco.cukes.sql.module;

import com.google.inject.matcher.Matchers;
import lv.ctco.cukes.core.extension.AbstractCukesModule;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.sql.interceptor.InflateContextInterceptor;

public abstract class BaseModule extends AbstractCukesModule {

	@Override
	protected void configure() {
		InflateContextInterceptor interceptor = new InflateContextInterceptor();
		requestInjection(interceptor);
		bindInterceptor(Matchers.annotatedWith(InflateContext.class), Matchers.any(), interceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(InflateContext.class), interceptor);
	}
}
