package net.sothatsit.json.data;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberByte extends JsonNumberWhole {

    private byte value;

    public JsonNumberByte(byte value) {
        super("Byte");

        this.value = value;
    }

    @Override
    public Byte getValue() {
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
        return this.value;
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
        return BigInteger.valueOf(this.value);
    }

    @Override
    public String asString() {
        return Byte.toString(this.value);
    }

    @Override
    public byte asByteExact() {
        return this.value;
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
        return Byte.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonNumber && this.value == ((JsonNumber) obj).asByte();
    }

}
