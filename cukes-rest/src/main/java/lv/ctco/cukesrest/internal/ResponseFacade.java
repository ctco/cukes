package lv.ctco.cukesrest.internal;

import com.google.common.base.Optional;
import com.google.inject.*;
import com.jayway.restassured.response.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.internal.matchers.*;
import lv.ctco.cukesrest.internal.switches.*;

import java.util.*;
import java.util.concurrent.*;

import static com.jayway.awaitility.Awaitility.*;

@Singleton
@InflateContext
public class ResponseFacade {

    @Inject
    RequestSpecificationFacade specification;
    @Inject
    GlobalWorldFacade world;
    @Inject
    Set<CukesRestPlugin> pluginSet;

    private Response response;
    private boolean expectException;
    private RuntimeException exception;

    public void doRequest(String httpMethod, final String url) throws Exception {
        final HttpMethod method = HttpMethod.parse(httpMethod);

        // TODO: Should be refactored into CukesRestPlugin
        boolean filterEnabled = world.getBoolean(CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS);
        AwaitCondition awaitCondition = specification.awaitCondition();
        try {
            if (awaitCondition != null && !filterEnabled) {
                int intervalTime = awaitCondition.getInterval().getValue();
                TimeUnit intervalUnit = awaitCondition.getInterval().getUnitDict().getTimeUnit();

                int waitTime = awaitCondition.getWaitTime().getValue();
                TimeUnit unit = awaitCondition.getWaitTime().getUnitDict().getTimeUnit();

                // TODO Fix
                with().pollInterval(intervalTime, intervalUnit).await().atMost(waitTime, unit).until(doRequest(url, method),
                    new AwaitConditionMatcher(awaitCondition));
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
        if (!$type.isPresent()) return;

        if ($type.get().equalsIgnoreCase("BASIC")) {
            authBasic();
        }
    }

    private Callable<ResponseWrapper> doRequest(final String url, final HttpMethod method) {
        return new Callable<ResponseWrapper>() {
            @Override
            public ResponseWrapper call() throws Exception {
                authenticate();
                for (CukesRestPlugin cukesRestPlugin : pluginSet) {
                    cukesRestPlugin.beforeRequest();
                }
                response = method.doRequest(specification.value(), url);
                for (CukesRestPlugin cukesRestPlugin : pluginSet) {
                    cukesRestPlugin.afterRequest();
                }
                return new ResponseWrapper(response);
            }
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
}
