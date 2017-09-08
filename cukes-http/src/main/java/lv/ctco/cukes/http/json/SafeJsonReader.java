package lv.ctco.cukes.http.json;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lv.ctco.cukes.core.CukesRuntimeException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

public class SafeJsonReader implements Iterable<JsonToken> {
    private JsonReader reader;
    private String currentPath;

    public SafeJsonReader(String json) {
        this(new StringReader(json));
    }

    public SafeJsonReader(Reader reader) {
        this.reader = new JsonReader(reader);
        this.reader.setLenient(true);
    }

    public JsonToken peek() {
        try {
            return reader.peek();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public String getPath() {
        String path = reader.getPath();
        return currentPath = path;
    }

    public void beginArray() {
        try {
            reader.beginArray();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void endArray() {
        try {
            reader.endArray();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void beginObject() {
        try {
            reader.beginObject();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void endObject() {
        try {
            reader.endObject();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void nextName() {
        try {
            reader.nextName();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public void nextNull() {
        try {
            reader.nextNull();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public String nextString() {
        try {
            return reader.nextString();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public boolean nextBoolean() {
        try {
            return reader.nextBoolean();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }

    @Override
    public Iterator<JsonToken> iterator() {
        return new Iterator<JsonToken>() {

            @Override
            public boolean hasNext() {
                JsonToken token = peek();
                return token != JsonToken.END_DOCUMENT;
            }

            @Override
            public JsonToken next() {
                getPath();
                return peek();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
