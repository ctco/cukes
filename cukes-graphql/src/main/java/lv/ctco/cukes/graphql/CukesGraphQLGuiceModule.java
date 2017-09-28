package lv.ctco.cukes.graphql;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.graphql.internal.PreprocessGraphQLRequestBody;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;

@CukesInjectableModule
public class CukesGraphQLGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<CukesHttpPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesHttpPlugin.class);
        multibinder.addBinding().to(PreprocessGraphQLRequestBody.class);
    }
}
