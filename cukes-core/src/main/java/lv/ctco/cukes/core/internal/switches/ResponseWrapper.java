package lv.ctco.cukes.core.internal.switches;


import io.restassured.response.Response;

@Deprecated // TODO: Need to be refactored
public class ResponseWrapper {

    private final Response response;

    public ResponseWrapper(Response response) {
        this.response = response;
    }

    public Integer statusCode() {
        return response.statusCode();
    }

    @Override // TODO: Finish
    public String toString() {
        return "statusCode<" + response.statusCode() + ">";
    }

    public Response getResponse() {
        return response;
    }
}
