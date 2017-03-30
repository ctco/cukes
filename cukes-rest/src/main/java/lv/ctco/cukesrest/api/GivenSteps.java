package lv.ctco.cukesrest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import io.restassured.http.ContentType;
import lv.ctco.cukesrest.CukesOptions;
import lv.ctco.cukesrest.internal.RequestSpecificationFacade;
import lv.ctco.cukesrest.internal.VariableFacade;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import lv.ctco.cukesrest.internal.resources.ResourceFileReader;

@Singleton
public class GivenSteps {

    @Inject
    RequestSpecificationFacade facade;

    @Inject
    VariableFacade variableFacade;

    @Inject
    GlobalWorldFacade world;

    @Inject
    ResourceFileReader reader;

    @Given("^let variable \"(.+)\" to be random UUID$")
    public void var_random_UUID(String varName) {
        this.variableFacade.setUUIDToVariable(varName);
    }

    @Given("^let variable \"(.+)\" equal to \"(.+)\"$")
    public void var_assigned(String varName, String value) {
        this.variableFacade.setVariable(varName, value);
    }

    @Given("^let variable \"([^\"]*)\" be equal to current timestamp$")
    public void letVariableBeEqualToCurrentTimestamp(String varName) {
        this.variableFacade.setCurrentTimestampToVariable(varName);
    }

    @Given("^value assertions are case-insensitive$")
    public void val_caseInsensitive() {
        this.world.put("case-insensitive", "true");
    }

    @Given("^baseUri is \"(.+)\"$")
    public void base_Uri_Is(String url) {
        this.facade.baseUri(url);
    }

    @Given("^resources root is \"(.+)\"$")
    public void resources_Root_Is(String url) {
        var_assigned(CukesOptions.RESOURCES_ROOT, url);
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

    @Given("^formParam \"(.+)\" is \"(.+)\"$")
    public void form_Param(String key, String value) {
        this.facade.formParam(key, value);
    }

    @Given("^request body \"(.+)\"$")
    public void request_Body(String body) {
        this.facade.body(body);
    }

    @Given("^request body:$")
    public void request_Body_From_Object(String body) {
        this.facade.body(body);
    }

    @Given("^request body from file \"(.+)\"$")
    public void request_Body_From_File(String path) {
        this.facade.body(this.reader.read(path));
    }

    @Given("^request body is a multipart file \"(.+)\"$")
    public void request_Body_Is_A_Multipart_File(String path) {
        this.facade.multiPart(this.reader.getResourceFile(path));
    }

    @Given("^request body is a multipart with control \"(.+)\" from file \"(.+)\"$")
    public void request_Body_Is_A_Multipart_File_With_Control(String control, String path) {
        this.facade.multiPart(this.reader.getResourceFile(path), control);
    }

    @Given("^request body is a multipart with mime-type \"(.+)\" and control \"(.+)\" from file \"(.+)\"$")
    public void request_Body_Is_A_Multipart_File_With_Control_Of_Type(String mimeType, String control, String path) {
        this.facade.multiPart(this.reader.getResourceFile(path), control, mimeType);
    }

    @Given("^request body is a multipart string \"(.+)\" with control \"(.+)\"$")
    public void request_Body_Is_A_Multipart_String_With_Control(String contentBody, String control) {
        this.facade.multiPart(contentBody, control);
    }

    @Given("^request body is a multipart string \"(.+)\" with mime-type \"(.+)\" and control \"(.+)\"$")
    public void request_Body_Is_A_Multipart_String_With_Control_Of_Type(String contentBody, String mimeType, String control) {
        this.facade.multiPart(contentBody, control, mimeType);
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
