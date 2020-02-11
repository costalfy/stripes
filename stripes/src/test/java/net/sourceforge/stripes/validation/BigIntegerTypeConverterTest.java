package net.sourceforge.stripes.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * Unit tests for the BigInteger class.
 */
public class BigIntegerTypeConverterTest {

    /**
     * Returns an empty collection of validation errors.
     */
    public Collection<ValidationError> errors() {
        return new ArrayList<>();
    }

    @Test

    public void basicParse() {
        TypeConverter<BigInteger> converter = new BigIntegerTypeConverter();
        converter.setLocale(Locale.US);
        BigInteger result = converter.convert("1234567",
                                              BigInteger.class,
                                              errors());
        Assertions.assertEquals(result,
                                new BigInteger("1234567"));
    }

    @Test

    public void parseBigNumber() {
        String number = Long.MAX_VALUE + "8729839871981298798234";
        TypeConverter<BigInteger> converter = new BigIntegerTypeConverter();
        converter.setLocale(Locale.US);
        BigInteger result = converter.convert(number,
                                              BigInteger.class,
                                              errors());
        Assertions.assertEquals(result,
                                new BigInteger(number));
        Assertions.assertEquals(result.toString(),
                                number);
    }

    @Test

    public void parseWithGroupingCharacters() {
        String number = "7297029872767869231987623498756389734567876534";
        String grouped = "7,297,029,872,767,869,231,987,623,498,756,389,734,567,876,534";
        TypeConverter<BigInteger> converter = new BigIntegerTypeConverter();
        converter.setLocale(Locale.US);
        BigInteger result = converter.convert(grouped,
                                              BigInteger.class,
                                              errors());
        Assertions.assertEquals(result,
                                new BigInteger(number));
    }

    @Test

    public void parseAlternateLocale() {
        String number = "123456789";
        String localized = "123.456.789";
        TypeConverter<BigInteger> converter = new BigIntegerTypeConverter();
        converter.setLocale(Locale.GERMANY);
        BigInteger result = converter.convert(localized,
                                              BigInteger.class,
                                              errors());
        Assertions.assertEquals(result,
                                new BigInteger(number));
    }

    @Test

    public void decimalTruncation() {
        TypeConverter<BigInteger> converter = new BigIntegerTypeConverter();
        converter.setLocale(Locale.US);
        BigInteger result = converter.convert("123456789.98765",
                                              BigInteger.class,
                                              errors());
        Assertions.assertEquals(result,
                                new BigInteger("123456789"));
        Assertions.assertEquals(result.toString(),
                                "123456789");
    }

    @Test

    public void invalidInput() {
        String number = "a1b2vc3d4";
        TypeConverter<BigInteger> converter = new BigIntegerTypeConverter();
        converter.setLocale(Locale.US);
        Collection<ValidationError> errors = errors();
        @SuppressWarnings("unused")
        BigInteger result = converter.convert(number,
                                              BigInteger.class,
                                              errors);
        Assertions.assertEquals(errors.size(),
                                1);
    }

}
