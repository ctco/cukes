package lv.ctco.cukes.core;

public class CukesRuntimeException extends RuntimeException {
    public CukesRuntimeException(Throwable cause) {
        super(cause);
    }

    public CukesRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CukesRuntimeException(String message) {
        super(message);
    }
}
