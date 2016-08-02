package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other;

public class JsonNumberBigDecimal extends JsonNumberDecimal {

    private BigDecimal value;

    public JsonNumberBigDecimal(BigDecimal value) {
        super("BigDecimal");

        if(value == null) {
            throw new JsonTypeException("BigDecimal value cannot be null");
        }

        if(value.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            throw new JsonTypeException("BigDecimals that can be represented as whole numbers should be");
        }

        this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public float asFloat() {
        return this.value.floatValue();
    }

    @Override
    public double asDouble() {
        return this.value.doubleValue();
    }

    @Override
    public byte asByte() {
        return this.value.byteValue();
    }

    @Override
    public short asShort() {
        return this.value.shortValue();
    }

    @Override
    public int asInt() {
        return this.value.intValue();
    }

    @Override
    public long asLong() {
        return this.value.longValue();
    }

    @Override
    public BigDecimal asBigDecimal() {
        return this.value;
    }

    @Override
    public BigInteger asBigInteger() {
        return this.value.toBigInteger();
    }

    @Override
    public String asString() {
        return this.value.toString();
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNumber && this.value.equals(((JsonNumber) obj).asBigDecimal());
    }

}
