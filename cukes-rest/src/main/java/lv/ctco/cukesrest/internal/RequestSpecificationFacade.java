package lv.ctco.cukesrest.internal;

import com.google.common.base.*;
import com.google.inject.*;
import com.jayway.restassured.*;
import com.jayway.restassured.path.json.config.*;
import com.jayway.restassured.specification.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.context.*;
import lv.ctco.cukesrest.internal.helpers.*;
import lv.ctco.cukesrest.internal.https.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;

import static com.jayway.restassured.config.JsonConfig.*;
import static com.jayway.restassured.config.RestAssuredConfig.*;
import static lv.ctco.cukesrest.internal.matchers.ResponseMatcher.*;
import static org.hamcrest.Matchers.*;

@Singleton
@InflateContext
public class RequestSpecificationFacade implements PropertyChangeListener {

    @Inject
    GlobalWorldFacade world;

    /* Mutable Builder */
    RequestSpecification specification;

    // TODO: Refactor
    private AwaitCondition awaitCondition;

    @Inject
    public RequestSpecificationFacade(GlobalWorldFacade world) {
        this.world = world;
        world.addPropertyChangeListener(CukesOptions.URL_ENCODING_ENABLED, this);
        world.addPropertyChangeListener(CukesOptions.PROXY, this);
        world.addPropertyChangeListener(CukesOptions.RELAXED_HTTPS, this);
        world.addPropertyChangeListener(CukesOptions.USERNAME, this);
        world.addPropertyChangeListener(CukesOptions.PASSWORD, this);
        initNewSpecification();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        onPropertyChange();
    }

    private void onPropertyChange() {
        // TODO: Refactor
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
            // TODO: Leak is present. Should have an ability to disable functionality
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
            onPropertyChange();
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

    public void setAwaitCondition(AwaitCondition awaitCondition) {
        this.awaitCondition = awaitCondition;
    }
}
