package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonNumberAccuracyException;

import java.math.BigInteger;

public abstract class JsonNumberWhole extends JsonNumber {

    public JsonNumberWhole(String elementName) {
        super(elementName);
    }

    @Override
    public boolean isWholeNumber() {
        return true;
    }

    @Override
    public byte asByteExact() {
        throw new JsonNumberAccuracyException(this.elementName + " will lose accuracy if represented as a Byte");
    }

    @Override
    public short asShortExact() {
        throw new JsonNumberAccuracyException(this.elementName + " will lose accuracy if represented as a Short");
    }

    @Override
    public int asIntExact() {
        throw new JsonNumberAccuracyException(this.elementName + " will lose accuracy if represented as an Integer");
    }

    @Override
    public long asLongExact() {
        throw new JsonNumberAccuracyException(this.elementName + " will lose accuracy if represented as a Long");
    }

    @Override
    public BigInteger asBigIntegerExact() {
        return asBigInteger();
    }

}
