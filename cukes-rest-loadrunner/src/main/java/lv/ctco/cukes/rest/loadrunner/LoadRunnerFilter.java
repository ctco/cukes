package lv.ctco.cukes.rest.loadrunner;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.rest.loadrunner.function.*;
import lv.ctco.cukes.rest.loadrunner.mapper.WebCustomRequestMapper;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Singleton
public class LoadRunnerFilter implements Filter {

    @Inject
    WebCustomRequestMapper mapper;

    @Inject
    GlobalWorldFacade globalWorldFacade;

    private List<LoadRunnerFunction> initializationFunctions = Arrays.asList(
        new InitializeSaveBoundedValueFunction(),
        new InitializeGenerateRandomStringFunction(),
        new InitializeGetUrlFunction(),
        new InitializeConcatFunction(),
        new InitializeRandomPasswordByPatternFunction()
    );
    private LoadRunnerAction action;
    private LoadRunnerTransaction trx;

    @Before
    public void beforeScenario(Scenario scenario) {
        createLoadRunnerTransaction(scenario.getName());
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        WebCustomRequest request = mapper.map(requestSpec);
        trx.addFunction(request);
        boolean blockRequests = globalWorldFacade.getBoolean(CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS);
        if (blockRequests) {
            Response response = Mockito.mock(Response.class);
            Mockito.when(response.then()).thenReturn(Mockito.mock(ValidatableResponse.class));
            return response;
        }
        return ctx.next(requestSpec, responseSpec);
    }

    public void createLoadRunnerAction() {
        action = new LoadRunnerAction();
    }

    public LoadRunnerTransaction getTrx() {
        return trx;
    }

    public void createLoadRunnerTransaction(String name) {
        trx = new LoadRunnerTransaction();
        trx.setName(name);
        trx.setTrxFlag("transactionStatus");
        if (action != null) {
            action.addTransaction(trx);
        }
    }

    public void dump(OutputStream out) {
        try {
            for (LoadRunnerFunction function : initializationFunctions) {
                out.write(function.format().getBytes());
            }
            if (action != null) out.write(action.format().getBytes());
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }
}
