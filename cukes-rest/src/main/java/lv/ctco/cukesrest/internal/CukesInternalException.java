package lv.ctco.cukesrest.internal;

public class CukesInternalException extends RuntimeException {
    public CukesInternalException(Throwable cause) {
        super(cause);
    }

    public CukesInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public CukesInternalException(String message) {
        super(message);
    }
}
