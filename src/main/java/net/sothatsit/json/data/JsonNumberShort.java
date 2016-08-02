package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberShort extends JsonNumberWhole {

    private short value;

    public JsonNumberShort(short value) {
        super("Short");

        if(value == (byte) value) {
            throw new JsonTypeException("Shorts that can be represented as Bytes should be");
        }

        this.value = value;
    }

    @Override
    public Short getValue() {
        return this.value;
    }

    @Override
    public float asFloat() {
        return (float) this.value;
    }

    @Override
    public double asDouble() {
        return (double) this.value;
    }

    @Override
    public byte asByte() {
        return (byte) this.value;
    }

    @Override
    public short asShort() {
        return this.value;
    }

    @Override
    public int asInt() {
        return (int) this.value;
    }

    @Override
    public long asLong() {
        return (long) this.value;
    }

    @Override
    public BigDecimal asBigDecimal() {
        return BigDecimal.valueOf(this.value);
    }

    @Override
    public BigInteger asBigInteger() {
        return BigInteger.valueOf(this.value);
    }

    @Override
    public String asString() {
        return Short.toString(this.value);
    }

    @Override
    public short asShortExact() {
        return this.value;
    }

    @Override
    public int asIntExact() {
        return this.value;
    }

    @Override
    public long asLongExact() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Short.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNumber && this.value == ((JsonNumber) obj).asShort();
    }

}
