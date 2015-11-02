package lv.ctco.cukesrest.internal.resources;

import com.google.inject.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.context.*;
import org.apache.commons.io.*;

import java.io.*;

public class FilePathService {

    @Inject
    GlobalWorldFacade world;

    public String normalize(String path) {
        if (isRelative(path)) {
            // TODO: Put correct RESOURCE_ROOT
            String resourceRoot = world.get(CukesOptions.RESOURCES_ROOT, "resources");
            return new File(resourceRoot, path).getAbsolutePath();
        }
        return new File(path).getAbsolutePath();
    }

    private boolean isRelative(String path) {
        return FilenameUtils.getPrefixLength(path) == 0;
    }
}
