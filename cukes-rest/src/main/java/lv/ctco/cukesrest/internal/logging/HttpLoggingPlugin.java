package lv.ctco.cukesrest.internal.logging;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.inject.Inject;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponseLogSpec;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.RequestLogSpecification;
import com.jayway.restassured.specification.RequestSpecification;
import lv.ctco.cukesrest.CukesRestPlugin;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.event.Level;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static com.jayway.restassured.config.LogConfig.logConfig;
import static lv.ctco.cukesrest.CukesOptions.*;
import static org.slf4j.LoggerFactory.getLogger;

public class HttpLoggingPlugin implements CukesRestPlugin {

    private final PrintStream logStream;
    private final GlobalWorldFacade world;

    @Inject
    public HttpLoggingPlugin(GlobalWorldFacade world) {
        this.world = world;
        logStream = new LoggerPrintStream(getLogger(world.get(OPTION_LOGGER_NAME, DEFAULT_LOGGER_NAME)), Level.INFO);
    }

    @Override
    public void beforeAllTests() {

    }

    @Override
    public void afterAllTests() {

    }

    @Override
    public void beforeScenario() {

    }

    @Override
    public void afterScenario() {

    }

    @Override
    public void beforeRequest(RequestSpecification requestSpecification) {
        RestAssuredConfig config;

        // need to do this until https://github.com/rest-assured/rest-assured/issues/824 is available
        if (requestSpecification instanceof FilterableRequestSpecification) {
            config = ((FilterableRequestSpecification) requestSpecification).getConfig();
        } else {
            throw new IllegalArgumentException(
                "Don't know how to retrieve current configuration from " + requestSpecification);
        }

        final RequestLogSpecification logSpec = requestSpecification
            .config(config.logConfig(logConfig().defaultStream(logStream)))
            .log();

        final List<LogDetail> logDetails = parseLogDetails(world.get(OPTION_REQUEST_INCLUDES, DEFAULT_REQUEST_INCLUDES));
        applyRequestLogDetails(logDetails, logSpec);
    }

    @Override
    public void afterRequest(Response response) {
        final List<LogDetail> logDetails = parseLogDetails(world.get(OPTION_RESPONSE_INCLUDES, DEFAULT_RESPONSE_INCLUDES));
        applyResponseLogDetails(logDetails, response.then().log());
    }

    private List<LogDetail> parseLogDetails(final String logDetailsString) {
        if (StringUtils.isBlank(logDetailsString)) return newArrayList();

        final HashSet<String> logDetailsStrings = newHashSet(Splitter.on(",").trimResults().omitEmptyStrings().split(logDetailsString));
        final HashSet<String> validLogDetailsStrings = newHashSet(transform(newArrayList(LogDetail.values()), new Function<LogDetail, String>() {
            @Override
            public String apply(LogDetail logDetail) {
                return logDetail.name().toLowerCase();
            }
        }));

        final Collection<LogDetail> logDetails = transform(intersection(logDetailsStrings, validLogDetailsStrings), new Function<String, LogDetail>() {
            @Override
            public LogDetail apply(String s) {
                return LogDetail.valueOf(s.toUpperCase());
            }
        });

        return newArrayList(logDetails.iterator());
    }

    private void applyRequestLogDetails(List<LogDetail> details, RequestLogSpecification logSpec) {
        if (details.isEmpty()) return;

        for (LogDetail detail : details) {
            switch (detail) {
                case ALL:
                    logSpec.all();
                    return;
                case BODY:
                    logSpec.body();
                    break;
                case COOKIES:
                    logSpec.cookies();
                    break;
                case HEADERS:
                    logSpec.headers();
                    break;
                case METHOD:
                    logSpec.method();
                    break;
                case PARAMS:
                    logSpec.parameters();
                    break;
                case PATH:
                    logSpec.path();
                    break;
            }
        }
    }

    private void applyResponseLogDetails(List<LogDetail> details, ValidatableResponseLogSpec logSpec) {
        if (details.isEmpty()) return;

        for (LogDetail detail : details) {
            switch (detail) {
                case ALL:
                    logSpec.all();
                    return;
                case BODY:
                    logSpec.body();
                    break;
                case COOKIES:
                    logSpec.cookies();
                    break;
                case HEADERS:
                    logSpec.headers();
                    break;
                case STATUS:
                    logSpec.status();
                    break;
            }
        }
    }
}
