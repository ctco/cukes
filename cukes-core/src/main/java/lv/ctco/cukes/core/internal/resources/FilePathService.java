package lv.ctco.cukes.core.internal.resources;

import com.google.inject.Inject;
import lv.ctco.cukes.core.CukesOptions;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import lv.ctco.cukes.core.internal.helpers.Files;

import java.io.File;

public class FilePathService {

    @Inject
    GlobalWorldFacade world;

    public String normalize(String path) {
        if (Files.isRelative(path)) {
            // TODO: Put correct RESOURCE_ROOT
            String resourceRoot = world.get(CukesOptions.RESOURCES_ROOT, "resources");
            return new File(resourceRoot, path).getAbsolutePath();
        }
        return new File(path).getAbsolutePath();
    }
}
