package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberFloat extends JsonNumberDecimal {

    private float value;

    public JsonNumberFloat(float value) {
        super("Float");

        if(value % 1 == 0) {
            throw new JsonTypeException("Floats that can be represented as whole numbers should be");
        }

        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public float asFloat() {
        return this.value;
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
        return (long) this.value;
    }

    @Override
    public BigDecimal asBigDecimal() {
        return BigDecimal.valueOf(this.value);
    }

    @Override
    public BigInteger asBigInteger() {
        return asBigDecimal().toBigInteger();
    }

    @Override
    public String asString() {
        return Float.toString(this.value);
    }

    @Override
    public int hashCode() {
        return Float.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNumber && this.value == ((JsonNumber) obj).asFloat();
    }

}
