package lv.ctco.cukesrest.loadrunner;

import com.google.common.base.*;
import com.google.inject.*;
import com.jayway.restassured.filter.*;
import com.jayway.restassured.response.*;
import com.jayway.restassured.specification.*;
import cucumber.api.*;
import cucumber.api.java.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.loadrunner.function.*;
import lv.ctco.cukesrest.loadrunner.mapper.*;
import org.mockito.*;

import java.io.*;

@Singleton
public class LoadRunnerFilter implements Filter {

    @Inject
    WebCustomRequestMapper mapper;

    @Inject
    GlobalWorldFacade globalWorldFacade;

    private LoadRunnerAction action;
    private LoadRunnerTransaction trx;

    @Before
    public void beforeScenario(Scenario scenario) {
        createLoadRunnerTransaction(scenario.getName());
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        WebCustomRequest request = mapper.map(requestSpec, ctx);
        trx.addFunction(request);
        boolean blockRequests = globalWorldFacade.getBoolean(CukesOptions.LOADRUNNER_FILTER_BLOCKS_REQUESTS);
        if (blockRequests) {
            return Mockito.mock(Response.class);
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
            if (action != null) out.write(action.format().getBytes());
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }
}
