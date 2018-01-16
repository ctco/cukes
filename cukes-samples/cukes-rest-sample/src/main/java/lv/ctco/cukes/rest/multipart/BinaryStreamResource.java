package lv.ctco.cukes.rest.multipart;

import com.google.inject.Inject;
import lv.ctco.cukes.rest.common.RestUtils;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/binary")
public class BinaryStreamResource {

    @Inject
    RestUtils rest;

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response upload(InputStream is) throws IOException {
        byte[] bytes = IOUtils.toByteArray(is);
        System.out.println("Read " + bytes.length + " byte(s)");
        return rest.ok(String.valueOf(bytes.length));
    }
}
