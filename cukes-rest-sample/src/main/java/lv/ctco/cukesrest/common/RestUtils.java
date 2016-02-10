package lv.ctco.cukesrest.common;

import com.google.inject.Inject;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;

import javax.ws.rs.core.Response;

import static lv.ctco.cukesrest.CukesOptions.BASE_URI;

public class RestUtils {

    @Inject
    GlobalWorldFacade globals;

    public Response ok() {
        return buildResponse(200);
    }

    public Response ok(Object data) {
        return buildResponse(200, data);
    }

    public Response created(Integer id, String resource) {
        return Response
            .status(201)
            .header("Location", String.format("%s%s/%s", globals.get(BASE_URI), resource, id))
            .build();
    }

    public Response notFound() {
        return buildResponse(404, "Object not found in the database");
    }

    public Response badRequest(String message) {
        return buildResponse(400, message);
    }

    public Response buildResponse(int statusCode) {
        return buildResponse(statusCode, null);
    }

    public Response buildResponse(int statusCode, Object data) {
        return Response
            .status(statusCode)
            .entity(data)
            .build();
    }
}
