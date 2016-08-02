package net.sothatsit.json.data;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JsonNumber extends AbstractJsonElement {

    public JsonNumber(String elementName) {
        super(elementName);
    }

    public abstract Number getValue();

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public JsonNumber asNumber() {
        return this;
    }

    public static JsonNumber create(byte value) {
        return new JsonNumberByte(value);
    }

    public static JsonNumber create(short value) {
        if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return new JsonNumberByte((byte) value);
        }

        return new JsonNumberShort(value);
    }

    public static JsonNumber create(int value) {
        if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return new JsonNumberByte((byte) value);
        }

        if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            return new JsonNumberShort((short) value);
        }

        return new JsonNumberInteger(value);
    }

    public static JsonNumber create(long value) {
        if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return new JsonNumberByte((byte) value);
        }

        if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            return new JsonNumberShort((short) value);
        }

        if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
            return new JsonNumberInteger((int) value);
        }

        return new JsonNumberLong(value);
    }

    public static JsonNumber create(BigInteger value) {
        if(value.compareTo(JsonNumberBigInteger.BYTE_MIN_VALUE) >= 0 && value.compareTo(JsonNumberBigInteger.BYTE_MAX_VALUE) <= 0) {
            return new JsonNumberByte(value.byteValue());
        }

        if(value.compareTo(JsonNumberBigInteger.SHORT_MIN_VALUE) >= 0 && value.compareTo(JsonNumberBigInteger.SHORT_MAX_VALUE) <= 0) {
            return new JsonNumberShort(value.shortValue());
        }

        if(value.compareTo(JsonNumberBigInteger.INTEGER_MIN_VALUE) >= 0 && value.compareTo(JsonNumberBigInteger.INTEGER_MAX_VALUE) <= 0) {
            return new JsonNumberInteger(value.intValue());
        }

        if(value.compareTo(JsonNumberBigInteger.LONG_MIN_VALUE) >= 0 && value.compareTo(JsonNumberBigInteger.LONG_MAX_VALUE) <= 0) {
            return new JsonNumberLong(value.longValue());
        }

        return new JsonNumberBigInteger(value);
    }

    public static JsonNumber create(float value) {
        if(value % 1 == 0) {
            if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                return new JsonNumberByte((byte) value);
            }

            if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                return new JsonNumberShort((short) value);
            }

            if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
                return new JsonNumberInteger((int) value);
            }

            if(value >= Long.MIN_VALUE && value <= Long.MAX_VALUE) {
                return new JsonNumberLong((long) value);
            }

            return new JsonNumberBigInteger(BigDecimal.valueOf(value).toBigInteger());
        }

        return new JsonNumberFloat(value);
    }

    public static JsonNumber create(double value) {
        if(value % 1 == 0) {
            if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                return new JsonNumberByte((byte) value);
            }

            if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                return new JsonNumberShort((short) value);
            }

            if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
                return new JsonNumberInteger((int) value);
            }

            if(value >= Long.MIN_VALUE && value <= Long.MAX_VALUE) {
                return new JsonNumberLong((long) value);
            }

            return new JsonNumberBigInteger(BigDecimal.valueOf(value).toBigInteger());
        }

        return new JsonNumberDouble(value);
    }

    public static JsonNumber create(BigDecimal value) {
        if(value.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return create(value.toBigInteger());
        }

        return new JsonNumberBigDecimal(value);
    }

}
