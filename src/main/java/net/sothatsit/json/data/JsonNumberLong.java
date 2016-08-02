package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberLong extends JsonNumberWhole {

    private long value;

    public JsonNumberLong(long value) {
        super("Long");

        if(value == (int) value) {
            throw new JsonTypeException("Longs that can be represented as Integers should be");
        }

        this.value = value;
    }

    @Override
    public Long getValue() {
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
        return (short) this.value;
    }

    @Override
    public int asInt() {
        return (int) this.value;
    }

    @Override
    public long asLong() {
        return this.value;
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
        return Long.toString(this.value);
    }

    @Override
    public long asLongExact() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNumber && this.value == ((JsonNumber) obj).asLong();
    }

}
