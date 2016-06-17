package lv.ctco.cukesrest.internal.helpers;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class IO {

    public static void closeQuietly(InputStream closeable) {
        try {
            if(closeable != null) {
                closeable.close();
            }
        } catch (IOException ignored) {}
    }

    public static List<String> readLines(InputStream inputStream, Charset encoding) throws IOException {
        InputStreamReader input = new InputStreamReader(inputStream, encoding == null?Charset.defaultCharset():encoding);
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<String>();

        for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            list.add(line);
        }

        return list;
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader?(BufferedReader)reader:new BufferedReader(reader);
    }
}
