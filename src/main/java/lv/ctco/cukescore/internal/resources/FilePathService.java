package lv.ctco.cukescore.internal.resources;

import com.google.inject.Inject;
import lv.ctco.cukescore.CukesOptions;
import lv.ctco.cukescore.internal.context.GlobalWorldFacade;
import lv.ctco.cukescore.internal.helpers.Files;

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
