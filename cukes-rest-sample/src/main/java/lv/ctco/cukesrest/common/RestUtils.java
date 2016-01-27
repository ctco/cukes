package lv.ctco.cukesrest.common;

import javax.ws.rs.core.Response;

public class RestUtils {

    public static Response ok() {
        return buildResponse(200);
    }

    public static Response ok(Object data) {
        return buildResponse(200, data);
    }

    public static Response created(Integer id) {
        return Response
            .status(201)
            .header("Location", "server-path-to-retrieve" + id)
            .build();
    }

    public static Response notFound() {
        return buildResponse(404, "Object not found in the database");
    }

    public static Response badRequest(String message) {
        return buildResponse(400, message);
    }

    public static Response buildResponse(int statusCode) {
        return buildResponse(statusCode, null);
    }

    public static Response buildResponse(int statusCode, Object data) {
        return Response
            .status(statusCode)
            .entity(data)
            .build();
    }
}
