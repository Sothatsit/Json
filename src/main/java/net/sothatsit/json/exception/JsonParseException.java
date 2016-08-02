package net.sothatsit.json.exception;

public class JsonParseException extends JsonException {

    public JsonParseException() {
        super();
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }

}
