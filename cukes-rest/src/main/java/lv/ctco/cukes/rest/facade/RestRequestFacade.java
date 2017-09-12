package lv.ctco.cukes.rest.facade;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.context.InflateContext;
import lv.ctco.cukes.core.internal.helpers.Time;
import lv.ctco.cukes.http.AwaitCondition;
import lv.ctco.cukes.http.RestAssuredConfiguration;
import lv.ctco.cukes.http.https.TrustAllTrustManager;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import static lv.ctco.cukes.http.matchers.ResponseMatcher.aHeader;
import static lv.ctco.cukes.http.matchers.ResponseMatcher.aProperty;
import static lv.ctco.cukes.http.matchers.ResponseMatcher.aStatusCode;
import static org.hamcrest.Matchers.equalTo;

@Singleton
@InflateContext
public class RestRequestFacade {

    @Inject
    private GlobalWorldFacade world;

    @Inject
    private RestAssuredConfiguration restConfig;

    private RequestSpecification specification;
    private AwaitCondition awaitCondition;
    private String requestBody;

    @Inject
    public RestRequestFacade(GlobalWorldFacade world, RestAssuredConfiguration restConfig) {
        this.world = world;
        this.restConfig = restConfig;
        initNewSpecification();
    }

    private void onCreate() {
        Optional<String> $baseUri = world.get(CukesOptions.BASE_URI);
        if ($baseUri.isPresent()) {
            baseUri($baseUri.get());
        }

        Optional<String> $proxy = world.get(CukesOptions.PROXY);
        if ($proxy.isPresent()) {
            URI uri;
            try {
                uri = new URI($proxy.get());
                specification.proxy(uri);
            } catch (URISyntaxException ignore) {
            }
        }

        boolean urlEncodingEnabled = world.getBoolean(CukesOptions.URL_ENCODING_ENABLED);
        specification.urlEncodingEnabled(urlEncodingEnabled);

        boolean relaxedHttps = world.getBoolean(CukesOptions.RELAXED_HTTPS);
        if (relaxedHttps) {
            specification.relaxedHTTPSValidation();
            TrustAllTrustManager.trustAllHttpsCertificates();
        }
    }

    public void param(String key, Object value) {
        specification.param(key, value);
    }

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

    public void formParam(String parameterName, String parameterValue) {
        specification.formParam(parameterName, parameterValue);
    }

    public void cookie(String cookieName, String cookieValue) {
        specification.cookie(cookieName, cookieValue);
    }

    public void header(String headerName, String headerValue) {
        specification.header(headerName, headerValue);
    }

    public void multiPart(String contentBody, String controlName) {
        specification.multiPart(controlName, contentBody);
    }

    public void multiPart(String contentBody, String controlName, String mimeType) {
        specification.multiPart(controlName, contentBody, mimeType);
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
            specification = RestAssured
                .given()
                .config(restConfig.getConfig());
            awaitCondition = null;
            onCreate();
        } catch (Exception e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void shouldWaitWithIntervalUntilStatusCodeReceived(Time waitTime, Time interval, int sCode) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, aStatusCode(equalTo(sCode)));
    }

    public void shouldWaitWithIntervalUntilStatusCodeReceived(Time waitTime, Time interval, int sCode, int failCode) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, aStatusCode(equalTo(sCode)), aStatusCode(equalTo(failCode)));
    }

    public void shouldWaitWithIntervalUntilPropertyEqualToValue(Time waitTime, Time interval, String property, String value) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, aProperty(property, equalTo(value)));
    }

    public void shouldWaitWithIntervalUntilPropertyEqualToValue(Time waitTime, Time interval, String property, String value, String failValue) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, aProperty(property, equalTo(value)), aProperty(property, equalTo(failValue)));
    }

    public void shouldWaitWithIntervalUntilHeaderEqualToValue(Time waitTime, Time interval, String header, String value) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, aHeader(header, equalTo(value)));
    }

    public void shouldWaitWithIntervalUntilHeaderEqualToValue(Time waitTime, Time interval, String header, String value, String failValue) {
        this.awaitCondition = new AwaitCondition(waitTime, interval, aHeader(header, equalTo(value)), aHeader(header, equalTo(failValue)));
    }

    public AwaitCondition awaitCondition() {
        return awaitCondition;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void clearRequestBody() {
        this.requestBody = null;
    }
}
