package net.sothatsit.json.exception;

public class JsonSyntaxException extends JsonException {

    public JsonSyntaxException() {
        super();
    }

    public JsonSyntaxException(String message) {
        super(message);
    }

    public JsonSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSyntaxException(Throwable cause) {
        super(cause);
    }

}
