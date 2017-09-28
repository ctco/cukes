package lv.ctco.cukes.rest.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.en.Given;
import lv.ctco.cukes.core.internal.resources.ResourceFileReader;
import lv.ctco.cukes.rest.facade.RestRequestFacade;

@Singleton
public class GivenSteps {

    @Inject
    RestRequestFacade facade;

    @Inject
    ResourceFileReader reader;

    @Given("^formParam \"(.+)\" is \"(.+)\"$")
    public void form_Param(String key, String value) {
        this.facade.formParam(key, value);
    }

    @Given("^request body \"(.+)\"$")
    public void request_Body(String body) {
        this.facade.setRequestBody(body);
    }

    @Given("^request body:$")
    public void request_Body_From_Object(String body) {
        this.facade.setRequestBody(body);
    }

    @Given("^request body from file \"(.+)\"$")
    public void request_Body_From_File(String path) {
        this.facade.setRequestBody(this.reader.read(path));
    }

    @Given("^request body is a multipart file \"(.+)\"$")
    public void request_Body_Is_A_Multipart_File(String path) {
        this.facade.multiPart(this.reader.read(path), "file", "application/octet-stream");
    }

    @Given("^request body is a multipart with control \"(.+)\" from file \"(.+)\"$")
    public void request_Body_Is_A_Multipart_File_With_Control(String control, String path) {
        this.facade.multiPart(this.reader.read(path), control);
    }

    @Given("^request body is a multipart with mime-type \"(.+)\" and control \"(.+)\" from file \"(.+)\"$")
    public void request_Body_Is_A_Multipart_File_With_Control_Of_Type(String mimeType, String control, String path) {
        this.facade.multiPart(this.reader.read(path), control, mimeType);
    }

    @Given("^request body is a multipart string \"(.+)\" with control \"(.+)\"$")
    public void request_Body_Is_A_Multipart_String_With_Control(String contentBody, String control) {
        this.facade.multiPart(contentBody, control);
    }

    @Given("^request body is a multipart string \"(.+)\" with mime-type \"(.+)\" and control \"(.+)\"$")
    public void request_Body_Is_A_Multipart_String_With_Control_Of_Type(String contentBody, String mimeType, String control) {
        this.facade.multiPart(contentBody, control, mimeType);
    }

}
