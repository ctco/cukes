package lv.ctco.cukes.http.logging;

import com.google.common.base.Splitter;
import com.google.inject.Inject;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseLogSpec;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.http.extension.CukesHttpPlugin;
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
import static io.restassured.filter.log.LogDetail.*;
import static lv.ctco.cukes.core.CukesOptions.*;
import static org.slf4j.LoggerFactory.getLogger;

public class HttpLoggingPlugin implements CukesHttpPlugin {

    private static final String DEFAULT_LOGGER_NAME = "lv.ctco.cukes.http";
    private static final String DEFAULT_REQUEST_INCLUDES = "";
    private static final String DEFAULT_RESPONSE_INCLUDES = "";

    private final PrintStream logStream;
    private final GlobalWorldFacade world;

    @Inject
    public HttpLoggingPlugin(GlobalWorldFacade world) {
        this.world = world;
        logStream = new LoggerPrintStream(getLogger(world.get(LOGGING_LOGGER_NAME, DEFAULT_LOGGER_NAME)), Level.INFO);
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
        if (!(requestSpecification instanceof FilterableRequestSpecification)) {
            throw new IllegalArgumentException("Cannot retrieve configuration from: " + requestSpecification);
        }

        final FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;

        final RestAssuredConfig config = (filterableRequestSpecification).getConfig();
        config.logConfig(config.getLogConfig().defaultStream(logStream));

        final RequestLogSpecification logSpec = filterableRequestSpecification.log();
        final List<LogDetail> logDetails = parseLogDetails(world.get(LOGGING_REQUEST_INCLUDES, DEFAULT_REQUEST_INCLUDES));

        if (logDetails.contains(ALL)) {
            logSpec.all();
        } else {
            if (logDetails.contains(METHOD))
                logSpec.method();
            if (logDetails.contains(URI))
                logSpec.uri();
            if (logDetails.contains(HEADERS))
                logSpec.headers();
            if (logDetails.contains(COOKIES))
                logSpec.cookies();
            if (logDetails.contains(PARAMS))
                logSpec.parameters();
            if (logDetails.contains(BODY))
                logSpec.body();
        }
    }

    @Override
    public void afterRequest(Response response) {
        final ValidatableResponseLogSpec<ValidatableResponse, Response> logSpec = response.then().log();
        final List<LogDetail> logDetails = parseLogDetails(world.get(LOGGING_RESPONSE_INCLUDES, DEFAULT_RESPONSE_INCLUDES));
        for (LogDetail detail : logDetails) {
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

    private List<LogDetail> parseLogDetails(final String logDetailsString) {
        if (StringUtils.isBlank(logDetailsString)) return newArrayList();

        final HashSet<String> logDetailsStrings = newHashSet(Splitter.on(",").trimResults().omitEmptyStrings().split(logDetailsString));
        final HashSet<String> validLogDetailsStrings = newHashSet(transform(newArrayList(LogDetail.values()),
            logDetail -> logDetail.name().toLowerCase()));

        final Collection<LogDetail> logDetails = transform(intersection(logDetailsStrings, validLogDetailsStrings),
            s -> LogDetail.valueOf(s.toUpperCase()));

        return newArrayList(logDetails.iterator());
    }
}
