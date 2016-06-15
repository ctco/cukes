package lv.ctco.cukesrest.internal.json;

import com.google.gson.stream.*;
import lv.ctco.cukesrest.*;

import java.io.*;
import java.util.*;

public class JsonParser {

    public Map<String, String> convertToPathToValueMap(String json) {
        try {
            Map<String, String> result = new HashMap<String, String>();
            parseJson(json, result);
            return result;
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    // TODO: Refactor
    static void parseJson(String json, Map<String, String> result) throws IOException {

        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        while (true) {
            JsonToken token = reader.peek();
            String path = reader.getPath();
            switch (token) {
                case BEGIN_ARRAY:
                    reader.beginArray();
                    break;
                case END_ARRAY:
                    reader.endArray();
                    break;
                case BEGIN_OBJECT:
                    reader.beginObject();
                    break;
                case END_OBJECT:
                    reader.endObject();
                    break;
                case NAME:
                    reader.nextName();
                    break;
                case STRING:
                    String s = reader.nextString();
                    add(path, s, result);
                    break;
                case NUMBER:
                    String n = reader.nextString();
                    add(path, n, result);
                    break;
                case BOOLEAN:
                    boolean b = reader.nextBoolean();
                    String str = toString(b);
                    add(path, str, result);
                    break;
                case NULL:
                    reader.nextNull();
                    break;
                case END_DOCUMENT:
                    return;
            }
        }
    }

    static private void add(String path, String value, Map<String, String> result) {
        if (path.startsWith("$.")) {
            path = path.substring(2);
        } else {
            path = path.substring(1);
        }
        result.put(path, value);
    }

    private static String toString(boolean b) {
        return Boolean.toString(b);
    }
}
