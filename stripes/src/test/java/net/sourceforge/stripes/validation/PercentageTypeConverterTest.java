package net.sourceforge.stripes.validation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * Unit tests for the PercentageTypeConverter class.
 */
public class PercentageTypeConverterTest {

    /**
     * Returns an empty collection of validation errors.
     */
    public Collection<ValidationError> errors() {
        return new ArrayList<>();
    }

    /**
     * Returns the type converter being tested.
     */
    public TypeConverter<Number> getConverter() {
        TypeConverter<Number> converter = new PercentageTypeConverter();
        converter.setLocale(Locale.US);
        return converter;
    }

    @Test(groups = "fast")
    public void parseBasic() {
        Number result = getConverter().convert("80%", Float.class, errors());
        Assert.assertEquals(result,
                            0.8f);
    }

    @Test(groups = "fast")
    public void parseSpaceBeforePercentSign() {
        Number result = getConverter().convert("80 %", Float.class, errors());
        Assert.assertEquals(result,
                            0.8f);
    }

    @Test(groups = "fast")
    public void parseWithoutPercentSign() {
        Number result = getConverter().convert("80", Float.class, errors());
        Assert.assertEquals(result,
                            0.8f);
    }

    @Test(groups = "fast")
    public void parseNegative() {
        Number result = getConverter().convert("-80%", Float.class, errors());
        Assert.assertEquals(result,
                            -0.8f);
    }

    @Test(groups = "fast")
    public void parseNegativeSpaceBeforePercentSign() {
        Number result = getConverter().convert("-80 %", Float.class, errors());
        Assert.assertEquals(result,
                            -0.8f);
    }

    @Test(groups = "fast")
    public void parseNegativeWithoutPercentSign() {
        Number result = getConverter().convert("-80", Float.class, errors());
        Assert.assertEquals(result,
                            -0.8f);
    }

    @Test(groups = "fast")
    public void parseParentheses() {
        Number result = getConverter().convert("(80%)", Float.class, errors());
        Assert.assertEquals(result,
                            -0.8f);
    }

    @Test(groups = "fast")
    public void parseParenthesesSpaceBeforePercentSign() {
        Number result = getConverter().convert("(80 %)", Float.class, errors());
        Assert.assertEquals(result,
                            -0.8f);
    }

    @Test(groups = "fast")
    public void parseParenthesesWithoutPercentSign() {
        Number result = getConverter().convert("(80)", Float.class, errors());
        Assert.assertEquals(result,
                            -0.8f);
    }

    @Test(groups = "fast")
    public void parseBasicDouble() {
        Number result = getConverter().convert("0.8%", Double.class, errors());
        Assert.assertEquals(result,
                            0.008);
    }

    @Test(groups = "fast")
    public void parseSpaceBeforePercentSignDouble() {
        Number result = getConverter().convert("0.8 %", Double.class, errors());
        Assert.assertEquals(result,
                            0.008);
    }

    @Test(groups = "fast")
    public void parseWithoutPercentSignDouble() {
        Number result = getConverter().convert("0.8", Double.class, errors());
        Assert.assertEquals(result,
                            0.008);
    }

    @Test(groups = "fast")
    public void parseNegativeDouble() {
        Number result = getConverter().convert("-0.8%", Double.class, errors());
        Assert.assertEquals(result,
                            -0.008);
    }

    @Test(groups = "fast")
    public void parseNegativeSpaceBeforePercentSignDouble() {
        Number result = getConverter().convert("-0.8 %", Double.class, errors());
        Assert.assertEquals(result,
                            -0.008);
    }

    @Test(groups = "fast")
    public void parseNegativeWithoutPercentSignDouble() {
        Number result = getConverter().convert("-0.8", Double.class, errors());
        Assert.assertEquals(result,
                            -0.008);
    }

    @Test(groups = "fast")
    public void parseParenthesesDouble() {
        Number result = getConverter().convert("(0.8%)", Double.class, errors());
        Assert.assertEquals(result,
                            -0.008);
    }

    @Test(groups = "fast")
    public void parseParenthesesSpaceBeforePercentSignDouble() {
        Number result = getConverter().convert("(0.8 %)", Double.class, errors());
        Assert.assertEquals(result,
                            -0.008);
    }

    @Test(groups = "fast")
    public void parseParenthesesWithoutPercentSignDouble() {
        Number result = getConverter().convert("(0.8)", Double.class, errors());
        Assert.assertEquals(result,
                            -0.008);
    }
}
