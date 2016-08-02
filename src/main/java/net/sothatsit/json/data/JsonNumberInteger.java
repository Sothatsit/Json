package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberInteger extends JsonNumberWhole {

    private int value;

    public JsonNumberInteger(int value) {
        super("Integer");

        if(value == (short) value) {
            throw new JsonTypeException("Integers that can be represented as Shorts should be");
        }

        this.value = value;
    }

    @Override
    public Integer getValue() {
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
        return this.value;
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
        return Integer.toString(this.value);
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
        return Integer.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNumber && this.value == ((JsonNumber) obj).asInt();
    }

}
