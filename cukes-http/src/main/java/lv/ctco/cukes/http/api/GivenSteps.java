package lv.ctco.cukes.http.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import io.restassured.http.ContentType;
import lv.ctco.cukes.http.facade.HttpRequestFacade;

@Singleton
public class GivenSteps {

    @Inject
    HttpRequestFacade facade;

    @Given("^baseUri is \"(.+)\"$")
    public void base_Uri_Is(String url) {
        this.facade.baseUri(url);
    }

    @Given("^proxy settings are \"(http|https)://([^:]+)(?::(\\d+))?\"$")
    public void proxy(String scheme, String host, Integer port) {
        this.facade.proxy(scheme, host, port);
    }

    @Given("^param \"(.+)\" \"(.+)\"$")
    public void param(String key, String value) {
        this.facade.param(key, value);
    }

    @Given("^accept \"(.+)\" mediaTypes$")
    public void accept(String mediaTypes) {
        this.facade.accept(mediaTypes);
    }

    @Given("^accept mediaType is JSON$")
    public void acceptJson() {
        this.facade.accept(ContentType.JSON.toString());
    }

    @Given("^content type is JSON$")
    public void content_Type_Json() {
        this.facade.contentType(ContentType.JSON.toString());
    }

    @Given("^content type is \"(.+)\"$")
    public void content_Type(String contentType) {
        this.facade.contentType(contentType);
    }

    @Given("^queryParam \"(.+)\" is \"(.+)\"$")
    public void query_Param(String key, String value) {
        this.facade.queryParam(key, value);
    }

    @Given("^session ID is \"(.+)\"$")
    public void session_ID(String sessionId) {
        this.facade.sessionId(sessionId);
    }

    @Given("^session ID \"(.+)\" with value \"(.+)\"$")
    public void session_ID_With_Value(String sessionId, String value) {
        this.facade.sessionId(sessionId, value);
    }

    @Given("^cookie \"(.+)\" with value \"(.+)\"$")
    public void cookie_With_Value(String cookie, String value) {
        this.facade.cookie(cookie, value);
    }

    @Given("^header ([^\"]+) with value \"(.+)\"$")
    public void header_With_Value(String name, String value) {
        this.facade.header(name, value);
    }

    @Given("^username \"(.+)\" and password \"(.+)\"$")
    public void authentication(String username, String password) {
        this.facade.authentication(username, password);
    }

    @Given("^authentication type is \"(.+)\"$")
    public void authentication(String authenticationType) {
        this.facade.authenticationType(authenticationType);
    }
}
