package net.sothatsit.json;

import net.sothatsit.json.data.JsonArray;
import net.sothatsit.json.data.JsonNull;
import net.sothatsit.json.data.JsonObject;
import net.sothatsit.json.exception.JsonEOSException;
import net.sothatsit.json.exception.JsonSyntaxException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonParserTest {

    @Test
    public void testStringEmpty() {
        Assert.assertEquals("", new JsonParser().parseString("\"\"").asString());
    }

    @Test
    public void testStringSimple() {
        String simpleChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789`~!@#$%^&*()-_=+[]{}|:;";

        Assert.assertEquals(simpleChars, new JsonParser().parseString("\"" + simpleChars + "\"").asString());
    }

    @Test
    public void testStringWhitespaceStart() {
        String simpleChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789`~!@#$%^&*()-_=+[]{}|:;";

        Assert.assertEquals(simpleChars, new JsonParser().parseString(" \n\t\f\r\"" + simpleChars + "\"").asString());
    }

    @Test
    public void testStringEscapedCharacters() {
        Assert.assertEquals(" \" \n ", new JsonParser().parseString("\" \\\" \\n \"").asString());
    }

    @Test
    public void testStringUnicodeCharacter() {
        Assert.assertEquals("\u058E", new JsonParser().parseString("\"\\u058E\"").asString());
    }

    @Test
    public void testStringInvalidEscapeFails() {
        try {
            new JsonParser().parseString("\"\\ \"");
            Assert.fail();
        } catch(JsonSyntaxException expected) {}
    }

    @Test
    public void testStringInvalidStartFails() {
        try {
            new JsonParser().parseString("  apple\"\\ \"");
            Assert.fail();
        } catch(JsonSyntaxException expected) {}
    }

    @Test
    public void testStringInvalidEndFails() {
        try {
            new JsonParser().parseString("\"\\ \"   apple");
            Assert.fail();
        } catch(JsonSyntaxException expected) {}
    }

    @Test
    public void testStringEndOfStreamFails() {
        try {
            new JsonParser().parseString("\"apple");
            Assert.fail();
        } catch(JsonEOSException expected) {}
    }

    @Test
    public void testByte() {
        Assert.assertEquals(5, new JsonParser().parseNumber("5").asByteExact());
    }

    @Test
    public void testNegativeByte() {
        Assert.assertEquals(-7, new JsonParser().parseNumber("-7").asByteExact());
    }

    @Test
    public void testShort() {
        Assert.assertEquals(500, new JsonParser().parseNumber("500").asShortExact());
    }

    @Test
    public void testNegativeShort() {
        Assert.assertEquals(-777, new JsonParser().parseNumber("-777").asShortExact());
    }

    @Test
    public void testInt() {
        Assert.assertEquals(50000, new JsonParser().parseNumber("50000").asIntExact());
    }

    @Test
    public void testNegativeInt() {
        Assert.assertEquals(-77777, new JsonParser().parseNumber("-77777").asIntExact());
    }

    @Test
    public void testLong() {
        Assert.assertEquals(5000000000L, new JsonParser().parseNumber("5000000000").asLongExact());
    }

    @Test
    public void testNegativeLong() {
        Assert.assertEquals(-7777777777L, new JsonParser().parseNumber("-7777777777").asLongExact());
    }

    @Test
    public void testBigInt() {
        Assert.assertEquals(new BigInteger("500000000000000000000"),
                new JsonParser().parseNumber("500000000000000000000").asBigIntegerExact());
    }

    @Test
    public void testNegativeBigInt() {
        Assert.assertEquals(new BigInteger("-777777777777777777777"),
                new JsonParser().parseNumber("-777777777777777777777").asBigIntegerExact());
    }

    @Test
    public void testIntExponential() {
        Assert.assertEquals(5000000, new JsonParser().parseNumber("5E6").asIntExact());
    }

    @Test
    public void testIntNegativeExponential() {
        Assert.assertEquals(5, new JsonParser().parseNumber("5000000E-6").asByteExact());
    }

    @Test
    public void testBigIntExponential() {
        Assert.assertEquals(new BigInteger("500000000000000000000"),
                new JsonParser().parseNumber("500000E15").asBigIntegerExact());
    }

    @Test
    public void testBigIntNegativeExponential() {
        Assert.assertEquals(5, new JsonParser().parseNumber("500000000000000000000E-20").asByteExact());
    }

    @Test
    public void testLongEmptyDecimal() {
        Assert.assertEquals(5000000000L, new JsonParser().parseNumber("5000000000.000").asLongExact());
    }

    @Test
    public void testBigIntEmptyDecimal() {
        Assert.assertEquals(new BigInteger("500000000000000000000"),
                new JsonParser().parseNumber("500000000000000000000.000000").asBigIntegerExact());
    }

    @Test
    public void testSmallDecimalNumber() {
        Assert.assertEquals(new BigDecimal("5.5"),
                new JsonParser().parseNumber("5.5").asBigDecimal());
    }

    @Test
    public void testDecimalNumber() {
        Assert.assertEquals(new BigDecimal("52342344.5126"),
                new JsonParser().parseNumber("52342344.5126").asBigDecimal());
    }

    @Test
    public void testBigDecimalNumber() {
        Assert.assertEquals(new BigDecimal("51245235745980876871345524.52357384675242637"),
                new JsonParser().parseNumber("51245235745980876871345524.52357384675242637").asBigDecimal());
    }

    @Test
    public void testDecimalNumberTrailingZeros() {
        Assert.assertEquals(new BigDecimal("52342344.5126"),
                new JsonParser().parseNumber("52342344.5126000").asBigDecimal());
    }

    @Test
    public void testBigDecimalNumberTrailingZeros() {
        Assert.assertEquals(new BigDecimal("51245235745980876871345524.52357384675242637"),
                new JsonParser().parseNumber("51245235745980876871345524.52357384675242637000000").asBigDecimal());
    }

    @Test
    public void testDecimalZero() {
        Assert.assertEquals(0, new JsonParser().parseNumber("0.0").asByteExact());
    }

    @Test
    public void testTrueBoolean() {
        Assert.assertEquals(true, new JsonParser().parseBoolean("true").asBoolean());
    }

    @Test
    public void testFalseBoolean() {
        Assert.assertEquals(false, new JsonParser().parseBoolean("false").asBoolean());
    }

    @Test
    public void testInvalidBooleanFails() {
        try {
            new JsonParser().parseBoolean("fald");
            Assert.fail();
        } catch(JsonSyntaxException expected) {}
    }

    @Test
    public void testBooleanEndOfStreamFails() {
        try {
            new JsonParser().parseBoolean("tru");
            Assert.fail();
        } catch(JsonEOSException expected) {}
    }

    @Test
    public void testNull() {
        Assert.assertEquals(JsonNull.INSTANCE, new JsonParser().parseNull("null"));
    }

    @Test
    public void testNumberArray() {
        Assert.assertEquals(JsonArray.build(1, 2, 3, 4, 5), new JsonParser().parseArray("[1, 2, 3, 4, 5]"));
    }

    @Test
    public void testStringArray() {
        Assert.assertEquals(JsonArray.build("Mary", "had", "a", "little", "lamb")
                , new JsonParser().parseArray("[\"Mary\", \"had\", \"a\", \"little\", \"lamb\"]"));
    }

    @Test
    public void testMixedArray() {
        Assert.assertEquals(JsonArray.build("apple", 5, 20.22, null, true, false)
                , new JsonParser().parseArray("[\"apple\", 5, 20.22, null, true, false]"));
    }

    @Test
    public void testArrayEmptyValueFails() {
        try {
            new JsonParser().parseArray("[1, 2, , 5]");
            Assert.fail();
        } catch(JsonSyntaxException expected) {}
    }

    @Test
    public void testSimpleObject() {
        Assert.assertEquals(JsonObject.newBuilder().set("name", "Mary").set("age", 25).set("height", 1.65).set("female", true).finish()
                , new JsonParser().parseObject("{\"name\": \"Mary\", \"age\": 25, \"height\": 1.65, \"female\": true}"));
    }

    @Test
    public void testNestedObjects() {
        Assert.assertEquals(JsonObject.newBuilder().set("name", "Mary").set("age", 25).set("height", 1.65).set("female", true)
                .set("friends", JsonArray.build(
                        JsonObject.newBuilder().set("name", "Bob").set("age", 24).finish(),
                        JsonObject.newBuilder().set("name", "Emily").set("age", 26).finish()
                )).finish()
                , new JsonParser().parseObject("{\"name\": \"Mary\", \"age\": 25, \"height\": 1.65, \"female\": true, " +
                        "\"friends\": [{\"name\": \"Bob\", \"age\": 24}, {\"name\": \"Emily\", \"age\": 26}]}"));
    }

}