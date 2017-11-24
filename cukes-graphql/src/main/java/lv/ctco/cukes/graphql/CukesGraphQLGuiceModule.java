package lv.ctco.cukes.graphql;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.graphql.internal.PreprocessGraphQLRequestBody;

@CukesInjectableModule
public class CukesGraphQLGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<CukesPlugin> multibinder = Multibinder.newSetBinder(binder(), CukesPlugin.class);
        multibinder.addBinding().to(PreprocessGraphQLRequestBody.class);
    }
}
