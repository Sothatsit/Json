package net.sothatsit.json.data;

public class JsonBoolean extends AbstractJsonElement {

    public static final JsonBoolean TRUE = new JsonBoolean(true);
    public static final JsonBoolean FALSE = new JsonBoolean(false);

    private boolean value;

    private JsonBoolean(boolean value) {
        super("Boolean");

        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public boolean asBoolean() {
        return this.value;
    }

    @Override
    public JsonBoolean asJsonBoolean() {
        return this;
    }

    @Override
    public String asString() {
        return (this.value ? "true" : "false");
    }

    public static JsonBoolean valueOf(boolean value) {
        return (value ? TRUE : FALSE);
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonBoolean && this.value == ((JsonBoolean) obj).getValue();
    }

}
