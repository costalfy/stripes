package net.sourceforge.stripes.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * Tests for the support class which helps out with number parsing and type
 * converting.
 *
 * @author Tim Fennell
 */
public class NumberTypeConverterSupportTest {

    /**
     * Helper method to fetch a US locale converter.
     */
    protected NumberTypeConverterSupport getConverter() {
        NumberTypeConverterSupport c = new NumberTypeConverterSupport();
        c.setLocale(Locale.US);
        return c;
    }

    @Test
    public void basicPositiveTests() {
        Number number = getConverter().parse("10912",
                                             new ArrayList<>());
        Assertions.assertEquals(number.intValue(),
                                10912);

        number = getConverter().parse("-1,000,000",
                                      new ArrayList<>());
        Assertions.assertEquals(number.intValue(),
                                -1000000);
    }

    @Test
    public void testNumbersWithWhiteSpace() {
        Number number = getConverter().parse("   5262  ",
                                             new ArrayList<>());
        Assertions.assertEquals(number.intValue(),
                                5262,
                                "White space should have no effect.");
    }

    @Test
    public void testFloatingPointsNumbers() {
        Number number = getConverter().parse("123.456",
                                             new ArrayList<>());
        Assertions.assertEquals(number.doubleValue(),
                                123.456);
    }

    @Test
    public void testParentheticalNumbers() {
        Number number = getConverter().parse("(891)",
                                             new ArrayList<>());
        Assertions.assertEquals(number.intValue(),
                                -891,
                                "Brackets mean negative values.");
    }

    @Test
    public void testCurrency() {
        Number number = getConverter().parse("$57",
                                             new ArrayList<>());
        Assertions.assertEquals(number.intValue(),
                                57);

        number = getConverter().parse("$1,999.95",
                                      new ArrayList<>());
        Assertions.assertEquals(number.doubleValue(),
                                1999.95);
    }

    @Test
    public void testCurrencyWithSpace() {
        Number number = getConverter().parse("$ 57",
                                             new ArrayList<>());
        Assertions.assertNotNull(number);
        Assertions.assertEquals(number.intValue(),
                                57);

        number = getConverter().parse("1,999.95 $",
                                      new ArrayList<>());
        Assertions.assertNotNull(number);
        Assertions.assertEquals(number.doubleValue(),
                                1999.95);
    }

    @Test
    public void testNegativeCurrency() {
        Number number = getConverter().parse("-$57",
                                             new ArrayList<>());
        Assertions.assertEquals(number.intValue(),
                                -57);

        number = getConverter().parse("$-57",
                                      new ArrayList<>());
        Assertions.assertEquals(number.intValue(),
                                -57);

        number = getConverter().parse("($1,999.95)",
                                      new ArrayList<>());
        Assertions.assertEquals(number.doubleValue(),
                                -1999.95);

        number = getConverter().parse("$(1,999.95)",
                                      new ArrayList<>());
        Assertions.assertEquals(number.doubleValue(),
                                -1999.95);
    }

    @Test
    public void testComplicatedString() {
        Number number = getConverter().parse("  ($2,154,123.66) ",
                                             new ArrayList<>());
        Assertions.assertEquals(number.doubleValue(),
                                -2154123.66);
    }

    @Test
    public void testWithText() {
        Collection<ValidationError> errors = new ArrayList<>();
        Number number = getConverter().parse("not-a-number",
                                             errors);
        Assertions.assertNull(number);
        Assertions.assertEquals(errors.size(),
                                1,
                                "We should have gotten a parse error.");
    }

    @Test
    public void testWithBogusTrailingText() {
        Collection<ValidationError> errors = new ArrayList<>();
        Number number = getConverter().parse("12345six",
                                             errors);
        Assertions.assertNull(number);
        Assertions.assertEquals(errors.size(),
                                1,
                                "We should have gotten a parse error.");
    }

    @Test
    public void testWithMultipleDecimalPoints() {
        Collection<ValidationError> errors = new ArrayList<>();
        Number number = getConverter().parse("123.456.789",
                                             errors);
        Assertions.assertNull(number);
        Assertions.assertEquals(errors.size(),
                                1,
                                "We should have gotten a parse error.");
    }
}
