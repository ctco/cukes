package lv.ctco.cukesrest.internal;

import com.google.inject.*;
import com.jayway.restassured.*;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.jayway.restassured.specification.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.internal.helpers.time.*;
import lv.ctco.cukesrest.internal.https.*;

import java.io.*;
import java.net.*;

import static com.jayway.restassured.config.JsonConfig.jsonConfig;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;
import static lv.ctco.cukesrest.internal.matchers.ResponseMatcher.*;
import static org.hamcrest.Matchers.*;

@Singleton
@InflateContext
public class RequestSpecificationFacade {

    @Inject
    GlobalWorldFacade world;

    /* Mutable Builder */
    RequestSpecification specification;

    // TODO: Refactor
    private AwaitCondition awaitCondition;

    @Inject
    public RequestSpecificationFacade(GlobalWorldFacade world) {
        this.world = world;
        initNewSpecification();
    }

    private void onCreate() {
        // TODO: Refactor
        String baseUri = world.get(CukesOptions.BASE_URI);
        if (baseUri != null) {
            baseUri(baseUri);
        }

        String proxy = world.get(CukesOptions.PROXY);
        if (proxy != null) {
            URI uri;
            try {
                uri = new URI(proxy);
                specification.proxy(uri);
            } catch (URISyntaxException ignore) {}
        }

        String urlEncodingEnabled = world.get(CukesOptions.URL_ENCODING_ENABLED);
        if (urlEncodingEnabled != null) {
            // TODO
            Boolean urlEncodingEnabledBool = Boolean.valueOf(urlEncodingEnabled);
            specification.urlEncodingEnabled(urlEncodingEnabledBool);
        }

        String relaxedHttps = world.get(CukesOptions.RELAXED_HTTPS);
        if (relaxedHttps != null) {
            // TODO
            Boolean relaxedHttpsEnabledBool = Boolean.valueOf(relaxedHttps);
            if (relaxedHttpsEnabledBool) {
                specification.relaxedHTTPSValidation();
                TrustAllTrustManager.trustAllHttpsCertificates();
            }
        }
    }

    public void param(String key, Object value) {
        specification.param(key, value);
    }

    // TODO: rework into array param
    public void queryParam(String parameterName, String parameterValue) {
        try {
            parameterValue = URLEncoder.encode(parameterValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }
        specification.queryParam(parameterName, parameterValue);
    }

    public void accept(String mediaTypes) {
        specification.accept(mediaTypes);
    }

    public void baseUri(String baseUri) {
        world.put(CukesOptions.BASE_URI, baseUri);
        specification.baseUri(baseUri);
    }

    public void contentType(String contentType) {
        specification.contentType(contentType);
    }

    // TODO: rework into array param
    public void formParam(String parameterName, String parameterValue) {
        specification.formParam(parameterName, parameterValue);
    }

    // TODO: rework into array param
    public void cookie(String cookieName, String cookieValue) {
        specification.cookie(cookieName, cookieValue);
    }

    // TODO: rework into array param
    public void header(String headerName, String headerValue) {
        specification.header(headerName, headerValue);
    }

    // TODO
    public void multiPart(String controlName, File file, String mimeType) {
        specification.multiPart(controlName, file, mimeType);
    }

    // TODO
    public void multiPart(String controlName, File file) {
        specification.multiPart(controlName, file);
    }

    public void proxy(String scheme, String host, Integer port) {
        if (port == null) port = 80;
        specification.proxy(host, port, scheme);
    }

    public void sessionId(String sessionIdValue) {
        specification.sessionId(sessionIdValue);
    }

    public void sessionId(String sessionIdName, String sessionIdValue) {
        specification.sessionId(sessionIdName, sessionIdValue);
    }

    public void body(String body) {
        specification.body(body);
    }

    public void authentication(String username, String password) {
        world.put(CukesOptions.USERNAME, username);
        world.put(CukesOptions.PASSWORD, password);
    }

    public void basicAuthentication(String username, String password) {
        specification.auth().basic(username, password);
    }

    public void authenticationType(String authenticationType) {
        world.put(CukesOptions.AUTH_TYPE, authenticationType);
    }

    public RequestSpecification value() {
        return specification;
    }

    public void initNewSpecification() {
        try {
            // TODO: Somehow this should be configurable
            specification = RestAssured.given()
                .config(newConfig().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)));
            awaitCondition = null;
            onCreate();
        } catch (Exception e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void shouldWaitWithIntervalUntilStatusCodeReceived(Time waitTime, Time interval, int sCode) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, statusCode(equalTo(sCode)));
    }

    public void shouldWaitWithIntervalUntilPropertyEqualToValue(Time waitTime, Time interval, String property, String value) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, property(property, equalTo(value)));
    }

    public AwaitCondition awaitCondition() {
        return awaitCondition;
    }

    public void setAwaitCondition(AwaitCondition awaitCondition) {
        this.awaitCondition = awaitCondition;
    }
}
