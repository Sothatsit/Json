package net.sothatsit.json.data;

public class JsonNull extends AbstractJsonElement {

    public static final JsonNull INSTANCE = new JsonNull();

    private JsonNull() {
        super("Null");
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String asString() {
        return "null";
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public JsonNull asNull() {
        return this;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNull;
    }

}
