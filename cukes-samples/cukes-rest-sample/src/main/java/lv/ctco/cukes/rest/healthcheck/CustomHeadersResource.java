package lv.ctco.cukes.rest.healthcheck;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
