package net.sothatsit.json;

import net.sothatsit.json.data.*;
import net.sothatsit.json.exception.JsonEOSException;
import net.sothatsit.json.exception.JsonSyntaxException;
import net.sothatsit.json.stream.CharReadStream;
import net.sothatsit.json.stream.StringReadStream;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonParser {

    private static final long UPSIZE_NUM = 922337203685477579L;

    private static final long[] LONG_TEN_POWERS = {
            1,
            10,
            100,
            1000,
            10000,
            100000,
            1000000,
            10000000,
            100000000,
            1000000000,
            10000000000L,
            100000000000L,
            1000000000000L,
            10000000000000L,
            100000000000000L,
            1000000000000000L,
            10000000000000000L,
            100000000000000000L,
            1000000000000000000L
    };

    private static final long[] LONG_MAX_VALUES = {
            0,
            9,
            92,
            922,
            9223,
            92233,
            922337,
            9223372,
            92233720,
            922337203,
            9223372036L,
            92233720368L,
            922337203685L,
            9223372036854L,
            92233720368547L,
            922337203685477L,
            9223372036854775L,
            92233720368547758L,
            922337203685477580L,
            9223372036854775807L
    };

    private static final BigInteger[] BIG_INTEGERS = {
            BigInteger.ZERO,
            BigInteger.ONE,
            BigInteger.valueOf(2),
            BigInteger.valueOf(3),
            BigInteger.valueOf(4),
            BigInteger.valueOf(5),
            BigInteger.valueOf(6),
            BigInteger.valueOf(7),
            BigInteger.valueOf(8),
            BigInteger.valueOf(9)
    };

    private static final char[] TRUE_EXPECTED_CHARS = "true".toCharArray();
    private static final char[] FALSE_EXPECTED_CHARS = "false".toCharArray();
    private static final char[] NULL_EXPECTED_CHARS = "null".toCharArray();

    public JsonString parseString(String json) {
        return this.parseString(new StringReadStream(json));
    }

    public JsonString parseString(CharReadStream stream) {
        JsonString parsed = this.parseString(stream, this.seekNonWhitespaceCharacter(stream));

        this.seekEndOfStream(stream);

        return parsed;
    }

    public JsonNumber parseNumber(String json) {
        return this.parseNumber(new StringReadStream(json));
    }

    public JsonNumber parseNumber(CharReadStream stream) {
        JsonNumber parsed = this.parseNumber(stream, this.seekNonWhitespaceCharacter(stream));

        this.seekEndOfStream(stream);

        return parsed;
    }

    public JsonBoolean parseBoolean(String json) {
        return this.parseBoolean(new StringReadStream(json));
    }

    public JsonBoolean parseBoolean(CharReadStream stream) {
        JsonBoolean parsed = this.parseBoolean(stream, this.seekNonWhitespaceCharacter(stream));

        this.seekEndOfStream(stream);

        return parsed;
    }

    public JsonNull parseNull(String json) {
        return this.parseNull(new StringReadStream(json));
    }

    public JsonNull parseNull(CharReadStream stream) {
        JsonNull parsed = this.parseNull(stream, this.seekNonWhitespaceCharacter(stream));

        this.seekEndOfStream(stream);

        return parsed;
    }

    public JsonArray parseArray(String json) {
        return this.parseArray(new StringReadStream(json));
    }

    public JsonArray parseArray(CharReadStream stream) {
        JsonArray parsed = this.parseArray(stream, this.seekNonWhitespaceCharacter(stream));

        this.seekEndOfStream(stream);

        return parsed;
    }

    public JsonObject parseObject(String json) {
        return this.parseObject(new StringReadStream(json));
    }

    public JsonObject parseObject(CharReadStream stream) {
        JsonObject parsed = this.parseObject(stream, this.seekNonWhitespaceCharacter(stream));

        this.seekEndOfStream(stream);

        return parsed;
    }

    public JsonElement parseElement(String json) {
        return this.parseElement(new StringReadStream(json));
    }

    public JsonElement parseElement(CharReadStream stream) {
        JsonElement parsed = this.parseElement(stream, this.seekNonWhitespaceCharacter(stream));

        this.seekEndOfStream(stream);

        return parsed;
    }

    private JsonElement parseElement(CharReadStream stream, char c) {
        if(c == 't' || c == 'f') {
            return this.parseBoolean(stream, c);
        } else if(c == 'n') {
            return this.parseNull(stream, c);
        } else if(c == '"') {
            return this.parseString(stream, c);
        } else if(c == '{') {
            return this.parseObject(stream, c);
        } else if(c == '[') {
            return this.parseArray(stream, c);
        } else if(c == '-' || Character.isDigit(c)) {
            return this.parseNumber(stream, c);
        } else {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing value at " + stream.getLocation());
        }
    }

    private JsonString parseString(CharReadStream stream, char c) {
        if(c != '"') {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while searching for start of String at "
                    + stream.getLocation() + ". Expected \" or whitespace");
        }

        StringBuilder builder = new StringBuilder();

        boolean escaped = false;

        while(true) {
            if(!stream.hasNext()) {
                throw new JsonEOSException("Unexpected end of stream while parsing string at " + stream.getLocation());
            }

            c = stream.next();

            if(escaped) {
                escaped = false;

                if(c == '"' || c == '\\' || c == '/') {
                    builder.append(c);
                } else if(c == 'b') {
                    builder.append('\b');
                } else if(c == 'f') {
                    builder.append('\f');
                } else if(c == 'n') {
                    builder.append('\n');
                } else if(c == 'r') {
                    builder.append('\r');
                } else if(c == 't') {
                    builder.append('\t');
                } else if(c == 'u') {
                    char hexCharacter = 0;

                    for(int i=0; i < 4; i++) {
                        if(stream.hasNext()) {
                            c = stream.next();

                            hexCharacter <<= 4;

                            if(c >= '0' && c <= '9') {
                                hexCharacter += c - '0';
                            } else if(c >= 'A' && c <= 'F') {
                                hexCharacter += (c - 'A') + 10;
                            } else if(c >= 'a' && c <= 'f') {
                                hexCharacter += (c - 'a') + 10;
                            } else {
                                throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing hex character at "
                                        + stream.getLocation() + ". Expected hex digit (0-9, A-F, a-f)");
                            }
                        } else {
                            throw new JsonEOSException("Unexpected end of stream while parsing hex character at "
                                    + stream.getLocation());
                        }
                    }

                    builder.append(hexCharacter);
                } else {
                    throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing escaped character at "
                            + stream.getLocation() + ". Expected \", \\, /, b, f, n, r, t or u");
                }
            } else {
                if(c == '\\') {
                    escaped = true;
                } else if(c == '"') {
                    break;
                } else {
                    builder.append(c);
                }
            }
        }

        return new JsonString(builder.toString());
    }

    private JsonObject parseObject(CharReadStream stream, char c) {
        if(c != '{') {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing object at " + stream.getLocation()
                    + ". Expected '{'");
        }

        JsonObject object = new JsonObject();

        c = seekNonWhitespaceCharacter(stream);

        if(c == '}') {
            return object;
        }

        while(true) {
            String key = parseString(stream, c).asString();

            c = seekNonWhitespaceCharacter(stream);

            if(c != ':') {
                throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing object at "
                        + stream.getLocation() + ". Expected ':'");
            }

            JsonElement element = parseElement(stream, seekNonWhitespaceCharacter(stream));

            object.set(key, element);

            c = seekNonWhitespaceCharacter(stream);

            if(c == '}') {
                return object;
            }

            if(c != ',') {
                throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing object at "
                        + stream.getLocation() + ". Expected ',' or '}'");
            }

            c = seekNonWhitespaceCharacter(stream);
        }
    }

    private JsonArray parseArray(CharReadStream stream, char c) {
        if(c != '[') {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing array at "
                    + stream.getLocation() + ". Expected '['");
        }

        JsonArray array = new JsonArray();

        c = seekNonWhitespaceCharacter(stream);

        if(c == ']') {
            return array;
        }

        while(true) {
            JsonElement element = parseElement(stream, c);

            array.add(element);

            c = seekNonWhitespaceCharacter(stream);

            if(c == ']') {
                return array;
            }

            if(c != ',') {
                throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing array at "
                        + stream.getLocation() + ". Expected ',' or ']'");
            }

            c = seekNonWhitespaceCharacter(stream);
        }
    }

    private JsonNull parseNull(CharReadStream stream, char c) {
        if(c != 'n') {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing null at "
                    + stream.getLocation() + ". Expected 'n'");
        }

        ReadExpectedError error = parseExpected(stream, NULL_EXPECTED_CHARS, 1, 4);

        if(error != null) {
            if(error.isEndOfStream()) {
                throw new JsonEOSException("Unexpected end of stream while parsing null at "
                        + stream.getLocation());
            } else {
                throw new JsonSyntaxException("Unexpected '" + error.getUnexpected() + "' found while parsing null at "
                        + stream.getLocation() + ". Expected '" + error.getExpected() + "'");
            }
        }

        return JsonNull.INSTANCE;
    }

    private JsonBoolean parseBoolean(CharReadStream stream, char c) {
        if(c != 't' && c != 'f') {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing boolean at "
                    + stream.getLocation() + ". Expected 't' or 'f'");
        }

        boolean isTrue = c == 't';

        ReadExpectedError error;

        if(isTrue) {
            error = parseExpected(stream, TRUE_EXPECTED_CHARS, 1, 4);
        } else {
            error = parseExpected(stream, FALSE_EXPECTED_CHARS, 1, 5);
        }

        if(error != null) {
            if(error.isEndOfStream()) {
                throw new JsonEOSException("Unexpected end of stream while parsing " + isTrue + " boolean at "
                        + stream.getLocation());
            } else {
                throw new JsonSyntaxException("Unexpected '" + error.getUnexpected() + "' found while parsing " + isTrue
                        + " boolean at " + stream.getLocation() + ". Expected '" + error.getExpected() + "'");
            }
        }

        return JsonBoolean.valueOf(isTrue);
    }

    private JsonNumber parseNumber(CharReadStream stream, char c) {
        byte sign;

        if(c == '-') {
            sign = -1;

            if(!stream.hasNext()) {
                throw new JsonEOSException("Unexpected end of stream while parsing number at "
                        + stream.getLocation());
            }

            c = stream.next();
        } else {
            sign = 1;
        }

        long intPart = c - '0';

        if(intPart < 0 || intPart > 9) {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing number at "
                    + stream.getLocation() + ". Expected 0-9");
        }

        int digits = 1;
        int trailingZeros = 0;

        boolean seenDecimalPoint = false;
        boolean isEndOfStream = false;
        int scale = 0;

        while(true) {
            if(!stream.hasNext()) {
                isEndOfStream = true;
                break;
            }

            c = stream.next();

            int val = c - '0';
            if(val >= 0 && val <= 9) {
                if(intPart > UPSIZE_NUM) {
                    return parseBigNumber(stream, intPart, val, digits, trailingZeros, sign, seenDecimalPoint, scale);
                }

                intPart = intPart * 10 + val;
                digits++;

                if(val == 0) {
                    trailingZeros++;
                } else {
                    trailingZeros = 0;
                }

                if(seenDecimalPoint) {
                    scale++;
                }
            } else if(c == '.') {
                if(seenDecimalPoint) {
                    break;
                }

                seenDecimalPoint = true;
            } else {
                break;
            }
        }

        int exponent;

        if(isEndOfStream) {
            exponent = -scale;
        } else if(c != 'e' && c != 'E') {
            stream.back();
            exponent = -scale;
        } else {
            exponent = parseExponent(stream) - scale;
        }

        if(intPart == 0) {
            return JsonNumber.create(0);
        }

        int newDigits = digits + exponent;

        if(trailingZeros + exponent < 0) {
            if(newDigits <= 17) {
                return JsonNumber.create((double) intPart * Math.pow(10, exponent));
            } else {
                return JsonNumber.create(new BigDecimal(BigInteger.valueOf(intPart), -exponent).stripTrailingZeros());
            }
        }

        if(newDigits > 19 || (newDigits == 19 && intPart > LONG_MAX_VALUES[digits])) {
            if(exponent > 0) {
                return JsonNumber.create(BigInteger.valueOf(sign * intPart)
                        .multiply(BigInteger.TEN.pow(exponent)));
            } else {
                return JsonNumber.create(BigInteger.valueOf(sign * intPart)
                        .divide(BigInteger.TEN.pow(-exponent)));
            }
        }

        if(exponent > 0) {
            return JsonNumber.create((sign * intPart) * LONG_TEN_POWERS[exponent]);
        } else {
            return JsonNumber.create((sign * intPart) / LONG_TEN_POWERS[-exponent]);
        }
    }

    private JsonNumber parseBigNumber(CharReadStream stream, long smallIntPart, int val, int digits, int trailingZeros, int sign, boolean seenDecimalPoint, int scale) {
        BigInteger intPart = BigInteger.valueOf(smallIntPart).multiply(BigInteger.TEN).add(BIG_INTEGERS[val]);

        digits++;

        if(val == 0) {
            trailingZeros++;
        } else {
            trailingZeros = 0;
        }

        if(seenDecimalPoint) {
            scale++;
        }

        boolean isEndOfStream = false;

        char c = ' ';

        while(true) {
            if(!stream.hasNext()) {
                isEndOfStream = true;
                break;
            }

            c = stream.next();

            val = c - '0';
            if(val >= 0 && val <= 9) {
                intPart = intPart.multiply(BigInteger.TEN).add(BIG_INTEGERS[val]);
                digits++;

                if(val == 0) {
                    trailingZeros++;
                } else {
                    trailingZeros = 0;
                }

                if(seenDecimalPoint) {
                    scale++;
                }
            } else if(c == '.') {
                if(seenDecimalPoint) {
                    break;
                }

                seenDecimalPoint = true;
            } else {
                break;
            }
        }

        int exponent;

        if(isEndOfStream) {
            exponent = -scale;
        } else if(c != 'e' && c != 'E') {
            stream.back();
            exponent = -scale;
        } else {
            exponent = parseExponent(stream) - scale;
        }

        if((trailingZeros + exponent) < 0) {
            return JsonNumber.create(new BigDecimal(intPart, -exponent).stripTrailingZeros());
        }

        if(exponent > 0) {
            return JsonNumber.create(intPart.multiply(BigInteger.TEN.pow(exponent)).multiply(BigInteger.valueOf(sign)));
        } else {
            return JsonNumber.create(intPart.divide(BigInteger.TEN.pow(-exponent)).multiply(BigInteger.valueOf(sign)));
        }
    }

    private int parseExponent(CharReadStream stream) {
        if(!stream.hasNext()) {
            throw new JsonEOSException("Unexpected end of stream while parsing exponent at "
                    + stream.getLocation());
        }

        char c = stream.next();

        int sign = 1;

        if(c == '-' || c == '+') {
            sign = (c == '+' ? 1 : -1);

            if(!stream.hasNext()) {
                throw new JsonEOSException("Unexpected end of stream while parsing exponent at "
                        + stream.getLocation());
            }

            c = stream.next();
        }

        int exponent = c - '0';

        if(exponent < 0 || exponent > 9) {
            throw new JsonSyntaxException("Unexpected '" + c + "' found while parsing exponent at "
                    + stream.getLocation() + ". Expected 0-9");
        }

        if(exponent == 0) {
            return 0;
        }

        while(true) {
            if(!stream.hasNext()) {
                return sign * exponent;
            }

            c = stream.next();
            int val = c - '0';

            if(val >= 0 && val <= 9) {
                exponent *= 10;
                exponent += val;
            } else {
                stream.back();
                return sign * exponent;
            }
        }
    }

    private char seekNonWhitespaceCharacter(CharReadStream stream) {
        char c;

        while(stream.hasNext()) {
            c = stream.next();

            if(!Character.isWhitespace(c)) {
                return c;
            }
        }

        throw new JsonEOSException("Unexpected end of stream while searching for a non whitespace character at "
                + stream.getLocation());
    }

    private void seekEndOfStream(CharReadStream stream) {
        char c;

        while(stream.hasNext()) {
            c = stream.next();

            if(!Character.isWhitespace(c)) {
                throw new JsonSyntaxException("Unexpected '" + c + "' found while searching for end of stream at "
                        + stream.getLocation() + ". Expected whitespace");
            }
        }
    }

    private ReadExpectedError parseExpected(CharReadStream stream, char[] expected, int start, int end) {
        char c;

        for(int index = start; index < end; index++) {
            if(!stream.hasNext()) {
                return ReadExpectedError.endOfStream();
            }

            c = stream.next();

            if(c != expected[index]) {
                return ReadExpectedError.unexpectedChar(c, expected[index]);
            }
        }

        return null;
    }

    private static class ReadExpectedError {

        private boolean endOfStream;
        private char unexpected;
        private char expected;

        private ReadExpectedError() {
            this.endOfStream = true;
        }

        private ReadExpectedError(char unexpected, char expected) {
            this.endOfStream = false;
            this.unexpected = unexpected;
            this.expected = expected;
        }

        public boolean isEndOfStream() {
            return this.endOfStream;
        }

        public char getUnexpected() {
            return this.unexpected;
        }

        public char getExpected() {
            return this.expected;
        }

        private static ReadExpectedError endOfStream() {
            return new ReadExpectedError();
        }

        private static ReadExpectedError unexpectedChar(char unexpected, char expected) {
            return new ReadExpectedError(unexpected, expected);
        }

    }

}