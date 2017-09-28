package lv.ctco.cukes.http.facade;

import io.restassured.response.Response;
import lv.ctco.cukes.core.internal.matchers.JsonMatchers;

public class ResponseContentProvider implements JsonMatchers.ContentProvider<Response> {

    public static final ResponseContentProvider INSTANCE = new ResponseContentProvider();

    @Override
    public String getValue(Object o) {
        return ((Response) o).getBody().asString();
    }

    @Override
    public String getContentType(Object o) {
        return ((Response) o).getContentType();
    }
}
