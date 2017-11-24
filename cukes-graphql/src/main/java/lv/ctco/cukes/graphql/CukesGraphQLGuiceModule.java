package lv.ctco.cukes.graphql;

import lv.ctco.cukes.core.extension.CukesInjectableModule;
import lv.ctco.cukes.graphql.internal.PreprocessGraphQLRequestBody;
import lv.ctco.cukes.http.extension.AbstractCukesHttpModule;

@CukesInjectableModule
public class CukesGraphQLGuiceModule extends AbstractCukesHttpModule {

    @Override
    protected void configure() {
        registerHttpPlugin(PreprocessGraphQLRequestBody.class);
    }
}
