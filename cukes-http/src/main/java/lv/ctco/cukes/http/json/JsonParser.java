package lv.ctco.cukes.http.json;

import com.google.gson.stream.JsonToken;

import java.util.HashMap;
import java.util.Map;

import static com.google.gson.stream.JsonToken.BEGIN_ARRAY;
import static com.google.gson.stream.JsonToken.BEGIN_OBJECT;
import static com.google.gson.stream.JsonToken.BOOLEAN;
import static com.google.gson.stream.JsonToken.END_ARRAY;
import static com.google.gson.stream.JsonToken.END_OBJECT;
import static com.google.gson.stream.JsonToken.NAME;
import static com.google.gson.stream.JsonToken.NULL;
import static com.google.gson.stream.JsonToken.NUMBER;
import static com.google.gson.stream.JsonToken.STRING;

public class JsonParser {

    public Map<String, String> parsePathToValueMap(String json) {
        Map<String, String> result = new HashMap<String, String>();
        SafeJsonReader reader = new SafeJsonReader(json);
        for (JsonToken token : reader) {
                 if (BEGIN_ARRAY == token)  reader.beginArray();
            else if (END_ARRAY == token)    reader.endArray();
            else if (BEGIN_OBJECT == token) reader.beginObject();
            else if (END_OBJECT == token)   reader.endObject();
            else if (NAME == token)         reader.nextName();
            else if (STRING == token)       add(reader.getCurrentPath(), reader.nextString(), result);
            else if (NUMBER == token)       add(reader.getCurrentPath(), reader.nextString(), result);
            else if (BOOLEAN == token)      add(reader.getCurrentPath(), Boolean.toString(reader.nextBoolean()), result);
            else if (NULL == token)         reader.nextNull();
        }
        reader.close();
        return result;
    }

    static private void add(String path, String value, Map<String, String> result) {
        if (path.startsWith("$.")) {
            path = path.substring(2);
        } else {
            path = path.substring(1);
        }
        result.put(path, value);
    }
}
