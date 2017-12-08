package lv.ctco.cukes.rest.multipart;

import com.google.inject.Inject;
import com.sun.jersey.multipart.FormDataParam;
import lv.ctco.cukes.rest.common.RestUtils;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/multipart")
public class MultipartResource {

    @Inject
    RestUtils rest;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("file") InputStream is) throws IOException {
        byte[] bytes = IOUtils.toByteArray(is);
        System.out.println("Read " + bytes.length + " byte(s)");
        return rest.ok(new String(bytes));
    }
}
