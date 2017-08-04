package lv.ctco.cukes.rest.common;

import com.google.inject.*;
import com.yammer.dropwizard.config.*;

import javax.ws.rs.core.*;

public class RestUtils {

    @Inject
    Configuration config;

    private static final String BASE_URI = "http://localhost:8080";

    public Response ok() {
        return buildResponse(200);
    }

    public Response ok(Object data) {
        return buildResponse(200, data);
    }

    public Response created(Integer id, String resource) {
        int port = config.getHttpConfiguration().getPort();
        return Response
            .status(201)
            .header("Location", String.format("http://localhost:%s%s/%s", port, resource, id))
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
