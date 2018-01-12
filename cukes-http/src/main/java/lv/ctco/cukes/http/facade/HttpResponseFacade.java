package lv.ctco.cukes.http.facade;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jayway.awaitility.Awaitility;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.http.AwaitCondition;
import lv.ctco.cukes.http.HttpMethod;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
import lv.ctco.cukes.http.matchers.AwaitConditionMatcher;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Singleton
@InflateContext
public class HttpResponseFacade {

    @Inject
    HttpRequestFacade specification;
    @Inject
    GlobalWorldFacade world;
    @Inject
    Set<CukesHttpPlugin> pluginSet;

    private Response response;
    private boolean expectException;
    private RuntimeException exception;
    private String responsePrefix;

    public void doRequest(String httpMethod, final String url) throws Exception {
        final HttpMethod method = HttpMethod.parse(httpMethod);

        // TODO: Should be refactored into CukesHttpPlugin
        boolean filterEnabled = world.getBoolean(CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS);
        AwaitCondition awaitCondition = specification.awaitCondition();
        try {
            if (awaitCondition != null && !filterEnabled) {
                int intervalTime = awaitCondition.getInterval().getValue();
                TimeUnit intervalUnit = awaitCondition.getInterval().getUnitDict().getTimeUnit();

                int waitTime = awaitCondition.getWaitTime().getValue();
                TimeUnit unit = awaitCondition.getWaitTime().getUnitDict().getTimeUnit();

                // TODO Fix
                Awaitility.with().pollInterval(intervalTime, intervalUnit)
                    .await()
                    .atMost(waitTime, unit)
                    .until(doRequest(url, method), new AwaitConditionMatcher(awaitCondition));
            } else {
                doRequest(url, method).call();
            }
        } catch (RuntimeException e) {
            if (!expectException) {
                throw e;
            }
            exception = e;
        }

        specification.initNewSpecification();
    }

    private void authenticate() {
        Optional<String> $type = world.get(CukesOptions.AUTH_TYPE);
        if (!$type.isPresent()) {
            return;
        }

        if ($type.get().equalsIgnoreCase("BASIC")) {
            authBasic();
        }
    }

    private Callable<Response> doRequest(final String url, final HttpMethod method) {
        final boolean filterEnabled = world.getBoolean(CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS);
        return () -> {
            authenticate();

            final RequestSpecification requestSpec = specification.value();

            for (CukesHttpPlugin plugin : pluginSet) {
                plugin.beforeRequest(requestSpec);
            }

            response = method.doRequest(requestSpec, url);

            for (CukesHttpPlugin plugin : pluginSet) {
                plugin.afterRequest(response);
            }
            if (!filterEnabled) {
                cacheHeaders(response);
            }
            return response;
        };
    }

    public Response response() {
        return response;
    }

    private void authBasic() {
        Optional<String> $username = world.get(CukesOptions.USERNAME);
        Optional<String> $password = world.get(CukesOptions.PASSWORD);
        if ($username.isPresent() && $password.isPresent()) {
            specification.basicAuthentication($username.get(), $password.get());
        }
    }

    public void setExpectException(boolean expectException) {
        this.expectException = expectException;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }

    public String getResponsePrefix() {
        return responsePrefix;
    }

    public void setResponsePrefix(String responsePrefix) {
        this.responsePrefix = responsePrefix;
    }

    private void cacheHeaders(Response response) {
        clearOldHeaders();
        Headers headers = response.getHeaders();
        for (Header header : headers) {
            String headerName = CukesOptions.HEADER_PREFIX + header.getName();
            world.put(headerName, header.getValue());
        }
    }

    private void clearOldHeaders() {
        Set<String> keys = world.getKeysStartingWith(CukesOptions.HEADER_PREFIX);
        for (String key : keys) {
            world.remove(key);
        }
    }

}
