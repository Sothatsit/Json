package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class AbstractJsonElement implements JsonElement {

    protected String elementName;

    public AbstractJsonElement(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public String asString() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a String");
    }

    @Override
    public JsonString asJsonString() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Json String");
    }

    @Override
    public boolean isCharacter() {
        return false;
    }

    @Override
    public char asCharacter() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Character");
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public JsonNumber asNumber() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a JSON Number");
    }

    @Override
    public boolean isDecimalNumber() {
        return false;
    }

    @Override
    public float asFloat() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Float");
    }

    @Override
    public double asDouble() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Double");
    }

    @Override
    public BigDecimal asBigDecimal() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a BigDecimal");
    }

    @Override
    public boolean isWholeNumber() {
        return false;
    }

    @Override
    public byte asByteExact() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Byte");
    }

    @Override
    public short asShortExact() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Short");
    }

    @Override
    public int asIntExact() {
        throw new JsonTypeException(this.elementName + " cannot be represented as an Integer");
    }

    @Override
    public long asLongExact() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Long");
    }

    @Override
    public BigInteger asBigIntegerExact() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a BigInteger");
    }

    @Override
    public byte asByte() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Byte");
    }

    @Override
    public short asShort() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Short");
    }

    @Override
    public int asInt() {
        throw new JsonTypeException(this.elementName + " cannot be represented as an Integer");
    }

    @Override
    public long asLong() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Long");
    }

    @Override
    public BigInteger asBigInteger() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a BigInteger");
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean asBoolean() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Boolean");
    }

    @Override
    public JsonBoolean asJsonBoolean() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a Json Boolean");
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public JsonObject asObject() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a JSON Object");
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public JsonArray asArray() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a JSON Array");
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public JsonNull asNull() {
        throw new JsonTypeException(this.elementName + " cannot be represented as a JSON Null");
    }

}
