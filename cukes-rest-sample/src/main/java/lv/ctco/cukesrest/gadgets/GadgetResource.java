package lv.ctco.cukesrest.gadgets;

import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static lv.ctco.cukesrest.common.RestUtils.badRequest;
import static lv.ctco.cukesrest.common.RestUtils.created;
import static lv.ctco.cukesrest.common.RestUtils.notFound;
import static lv.ctco.cukesrest.common.RestUtils.ok;

@SuppressWarnings("SameReturnValue")
@Path("/gadgets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GadgetResource {

    @Inject
    GadgetService service;

    @GET
    public Response searchGadgets() {
        List<GadgetDto> gadgets = service.searchGadgets();
        return ok(gadgets);
    }

    @GET
    @Path("{id}")
    public Response getGadget(@PathParam("id") Long id) {
        GadgetDto gadget = service.getGadget(id);
        if (gadget == null) {
            return notFound();
        }
        return ok(gadget);
    }

    @POST
    public Response addGadget(GadgetDto gadget) {
        Long id = service.addGadget(gadget);
        if (id == null) {
            return badRequest("Could not add new Gadget");
        }
        return created(id);
    }

    @PUT
    @Path("{id}")
    public Response updateGadget(@PathParam("id") Long id, GadgetDto gadget) {
        boolean result = service.updateGadget(id, gadget);
        if (!result) {
            return badRequest("Could add update Gadget with ID: " + id);
        }
        return ok();
    }

    @DELETE
    @Path("{id}")
    public Response removeGadget(@PathParam("id") Long id) {
        boolean result = service.removeGadget(id);
        if (!result) {
            return notFound();
        }
        return ok();
    }
}
