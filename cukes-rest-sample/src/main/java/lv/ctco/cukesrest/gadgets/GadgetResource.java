package lv.ctco.cukesrest.gadgets;

import com.google.inject.Inject;
import lv.ctco.cukesrest.common.RestUtils;
import lv.ctco.cukesrest.gadgets.dto.GadgetData;
import lv.ctco.cukesrest.gadgets.dto.GadgetDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@SuppressWarnings("SameReturnValue")
@Path(GadgetResource.API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GadgetResource {

    protected static final String API = "/gadgets";

    @Inject
    DummyGadgetService service;
    @Inject
    RestUtils rest;

    @GET
    public Response searchGadgets() {
        Collection<GadgetDto> gadgets = service.searchGadgets();
        return rest.ok(new GadgetData(gadgets));
    }

    @GET
    @Path("{id}")
    public Response getGadget(@PathParam("id") Integer id) {
        GadgetDto gadget = service.getGadget(id);
        if (gadget == null) {
            return rest.notFound();
        }
        return rest.ok(gadget);
    }

    @POST
    public Response addGadget(GadgetDto gadget) {
        Integer id = service.addGadget(gadget);
        if (id == null) {
            return rest.badRequest("Could not add new Gadget");
        }
        return rest.created(id, API);
    }

    @PUT
    @Path("{id}")
    public Response updateGadget(@PathParam("id") Integer id, GadgetDto gadget) {
        boolean result = service.updateGadget(id, gadget);
        if (!result) {
            return rest.badRequest("Could not update Gadget with ID: " + id);
        }
        return rest.ok();
    }

    @DELETE
    @Path("{id}")
    public Response removeGadget(@PathParam("id") Integer id) {
        boolean result = service.removeGadget(id);
        if (!result) {
            return rest.notFound();
        }
        return rest.ok();
    }
}
