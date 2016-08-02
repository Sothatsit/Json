package net.sothatsit.json.data;

import net.sothatsit.json.exception.JsonTypeException;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface JsonElement {

    public boolean isString();

    public String asString();

    public JsonString asJsonString();

    public boolean isCharacter();

    public char asCharacter();

    public boolean isNumber();

    public JsonNumber asNumber();

    public boolean isDecimalNumber();

    public float asFloat();

    public double asDouble();

    public BigDecimal asBigDecimal();

    public boolean isWholeNumber();

    public byte asByte();

    public byte asByteExact();

    public short asShort();

    public short asShortExact();

    public int asInt();

    public int asIntExact();

    public long asLong();

    public long asLongExact();

    public BigInteger asBigInteger();

    public BigInteger asBigIntegerExact();

    public boolean isBoolean();

    public boolean asBoolean();

    public JsonBoolean asJsonBoolean();

    public boolean isObject();

    public JsonObject asObject();

    public boolean isArray();

    public JsonArray asArray();

    public boolean isNull();

    public JsonNull asNull();

    public Object getValue();

    public static JsonElement fromObject(Object value) {
        if(value == null) {
            return JsonNull.INSTANCE;
        }

        if(value instanceof JsonElement) {
            return (JsonElement) value;
        }

        if(value instanceof Map<?, ?>) {
            Map<String, JsonElement> converted = new HashMap<>(((Map<?, ?>) value).size());

            for(Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                if(entry.getKey() == null) {
                    throw new JsonTypeException("Json Objects cannot be constructed from a map with a null key");
                }

                if(!(entry.getKey() instanceof String)) {
                    throw new JsonTypeException("Json Objects cannot be constructed from a map with a non-string key");
                }

                converted.put((String) entry.getKey(), JsonElement.fromObject(entry.getValue()));
            }

            return new JsonObject(converted);
        }

        if(value instanceof List<?>) {
            List<JsonElement> converted = new ArrayList<>(((List<?>) value).size());

            for(Object element : (List<?>) value) {
                converted.add(JsonElement.fromObject(element));
            }

            return new JsonArray(converted);
        }

        if(value.getClass().isArray()) {
            int length = Array.getLength(value);

            List<JsonElement> converted = new ArrayList<>(length);

            for(int index = 0; index < length; index++) {
                converted.add(JsonElement.fromObject(Array.get(value, index)));
            }

            return new JsonArray(converted);
        }

        if(value instanceof String) {
            return new JsonString((String) value);
        }

        if(value instanceof Character) {
            return new JsonString((Character) value);
        }

        if(value instanceof Boolean) {
            return JsonBoolean.valueOf((Boolean) value);
        }

        if(value instanceof Float) {
            return JsonNumber.create((Float) value);
        }

        if(value instanceof Double) {
            return JsonNumber.create((Double) value);
        }

        if(value instanceof BigDecimal) {
            return JsonNumber.create((BigDecimal) value);
        }

        if(value instanceof Byte) {
            return JsonNumber.create((Byte) value);
        }

        if(value instanceof Short) {
            return JsonNumber.create((Short) value);
        }

        if(value instanceof Integer) {
            return JsonNumber.create((Integer) value);
        }

        if(value instanceof Long) {
            return JsonNumber.create((Long) value);
        }

        if(value instanceof BigInteger) {
            return JsonNumber.create((BigInteger) value);
        }

        throw new JsonTypeException("Unknown JsonElement implementation for data type " + value.getClass());
    }

}
