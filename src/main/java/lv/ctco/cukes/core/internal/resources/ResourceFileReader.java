package lv.ctco.cukes.core.internal.resources;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.helpers.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResourceFileReader {

    @Inject
    FilePathService pathService;

    public String read(String path) {
        return Joiner.on("").join(readLines(path));
    }

    public List<String> readLines(String path) {
        try {
            File file = getResourceFile(path);
            return Files.readLines(file);
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public File getResourceFile(String relativePath) {
        String normalizedPath = pathService.normalize(relativePath);
        return new File(normalizedPath);
    }
}
