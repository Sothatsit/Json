package net.sothatsit.json.exception;

public class JsonTypeException extends JsonException {

    public JsonTypeException() {
        super();
    }

    public JsonTypeException(String message) {
        super(message);
    }

    public JsonTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonTypeException(Throwable cause) {
        super(cause);
    }

}
