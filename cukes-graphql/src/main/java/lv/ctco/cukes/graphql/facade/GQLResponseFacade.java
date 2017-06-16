package lv.ctco.cukes.graphql.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.extension.CukesPlugin;
import lv.ctco.cukes.core.internal.HttpMethod;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.switches.ResponseWrapper;

import java.util.Set;
import java.util.concurrent.Callable;

@Singleton
@InflateContext
public class GQLResponseFacade {

    @Inject
    GQLRequestFacade requestFacade;

    @Inject
    GlobalWorldFacade world;

    @Inject
    Set<CukesPlugin> pluginSet;

    private Response response;

    public void doRequest() throws Exception {
        requestFacade.body(requestFacade.getGraphQLRequest());
        doRequest(HttpMethod.POST).call();

        requestFacade.initNewSpecification();
    }

    private Callable<ResponseWrapper> doRequest(final HttpMethod method) {
        return new Callable<ResponseWrapper>() {
            @Override
            public ResponseWrapper call() throws Exception {
                final RequestSpecification requestSpec = requestFacade.value();

                for (CukesPlugin CukesPlugin : pluginSet) {
                    CukesPlugin.beforeRequest(requestSpec);
                }

                response = method.doRequest(requestSpec);

                for (CukesPlugin CukesPlugin : pluginSet) {
                    CukesPlugin.afterRequest(response);
                }
                cacheHeaders(response);
                return new ResponseWrapper(response);
            }
        };
    }

    private void clearOldHeaders() {
        Set<String> keys = world.getKeysStartingWith(CukesOptions.HEADER_PREFIX);
        for (String key : keys) {
            world.remove(key);
        }
    }

    private void cacheHeaders(Response response) {
        clearOldHeaders();
        Headers headers = response.getHeaders();
        for (Header header : headers) {
            String headerName = CukesOptions.HEADER_PREFIX + header.getName();
            world.put(headerName, header.getValue());
        }
    }

    public Response response() {
        return response;
    }
}
