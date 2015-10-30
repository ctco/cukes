package lv.ctco.cukesrest.internal.resources;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import lv.ctco.cukesrest.internal.CukesInternalException;
import org.apache.commons.io.FileUtils;

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
            String normalizedPath = pathService.normalize(path);
            File file = new File(normalizedPath);
            return FileUtils.readLines(file);
        } catch (IOException e) {
            throw new CukesInternalException(e);
        }
    }
}
