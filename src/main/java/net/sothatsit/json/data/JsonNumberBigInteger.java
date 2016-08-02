package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberBigInteger extends JsonNumberWhole {

    public static final BigInteger BYTE_MAX_VALUE = BigInteger.valueOf(Byte.MAX_VALUE);
    public static final BigInteger BYTE_MIN_VALUE = BigInteger.valueOf(Byte.MIN_VALUE);
    public static final BigInteger SHORT_MAX_VALUE = BigInteger.valueOf(Short.MAX_VALUE);
    public static final BigInteger SHORT_MIN_VALUE = BigInteger.valueOf(Short.MIN_VALUE);
    public static final BigInteger INTEGER_MAX_VALUE = BigInteger.valueOf(Integer.MAX_VALUE);
    public static final BigInteger INTEGER_MIN_VALUE = BigInteger.valueOf(Integer.MIN_VALUE);
    public static final BigInteger LONG_MAX_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    public static final BigInteger LONG_MIN_VALUE = BigInteger.valueOf(Long.MIN_VALUE);

    private BigInteger value;

    public JsonNumberBigInteger(BigInteger value) {
        super("BigInteger");

        if(value.compareTo(BigInteger.valueOf(value.longValue())) == 0) {
            throw new JsonTypeException("BigIntegers that can be represented as Longs should be");
        }

        this.value = value;
    }

    @Override
    public BigInteger getValue() {
        return this.value;
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
        return new BigDecimal(this.value);
    }

    @Override
    public BigInteger asBigInteger() {
        return this.value;
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
        return obj instanceof JsonNumber && this.value.equals(((JsonNumber) obj).asBigInteger());
    }

}
