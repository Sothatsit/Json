package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberDouble extends JsonNumberDecimal {

    private double value;

    public JsonNumberDouble(double value) {
        super("Double");

        if(value % 1 == 0) {
            throw new JsonTypeException("Doubles that can be represented as whole numbers should be");
        }

        this.value = value;
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public float asFloat() {
        return (float) this.value;
    }

    @Override
    public double asDouble() {
        return this.value;
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
        return Double.toString(this.value);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNumber && this.value == ((JsonNumber) obj).asDouble();
    }

}
