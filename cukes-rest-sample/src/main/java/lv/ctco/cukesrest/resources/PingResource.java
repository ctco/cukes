package lv.ctco.cukesrest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@SuppressWarnings("SameReturnValue")
@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    @GET
    public String ping() {
        return "pong";
    }
}
