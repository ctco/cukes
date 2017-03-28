package lv.ctco.cukesrest.internal.resources;

import com.google.common.base.*;
import com.google.inject.*;
import lv.ctco.cukesrest.*;
import lv.ctco.cukesrest.internal.helpers.*;

import java.io.*;
import java.util.*;

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
