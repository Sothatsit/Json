package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

public class JsonString extends AbstractJsonElement {

    private String value;

    public JsonString(Character value) {
        this(Character.toString(value));
    }

    public JsonString(String value) {
        super("String");

        this.value = value;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String asString() {
        return this.value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public JsonString asJsonString() {
        return this;
    }

    @Override
    public boolean isCharacter() {
        return this.value.length() == 1;
    }

    @Override
    public char asCharacter() {
        if(this.value.length() != 1) {
            throw new JsonTypeException("Only Strings of length 1 can be represented as a Character");
        } else {
            return this.value.charAt(0);
        }
    }

    @Override
    public boolean asBoolean() {
        if(this.value.equals("true")) {
            return true;
        } else if(this.value.equals("false")) {
            return false;
        } else {
            throw new JsonTypeException("Only Strings that are \"true\" or \"false\" can be represented as a Boolean");
        }
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonString && this.value.equals(((JsonString) obj).asString());
    }

}
