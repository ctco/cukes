package lv.ctco.cukes.mock.rest.internal;

public class MockResponse {


    private String httpMethod;
    private String url;
    private String receivedBody;

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setReceivedBody(String receivedBody) {
        this.receivedBody = receivedBody;
    }

    public String getRequestBody() {
        return receivedBody;
    }
}
