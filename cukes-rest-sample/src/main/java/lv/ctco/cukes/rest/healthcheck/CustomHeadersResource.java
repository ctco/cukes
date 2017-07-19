package lv.ctco.cukes.rest.healthcheck;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path(CustomHeadersResource.API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomHeadersResource {

    protected static final String API = "/customHeaders";

    @GET
    public Response headers() {
        return Response.ok()
            .header("Custom-Header", "2000000000000")
            .build();
    }
}
