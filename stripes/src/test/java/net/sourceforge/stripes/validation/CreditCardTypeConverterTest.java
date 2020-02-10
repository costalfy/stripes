package net.sourceforge.stripes.validation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class CreditCardTypeConverterTest {

    @Test(groups = "fast")
    public void validNumber() {
        CreditCardTypeConverter c = new CreditCardTypeConverter();
        Assert.assertEquals(c.convert("4111111111111111", String.class,
                                      new ArrayList<>()), "4111111111111111");
    }

    @Test(groups = "fast")
    public void invalidNumber() {
        CreditCardTypeConverter c = new CreditCardTypeConverter();
        Assert.assertNull(c.convert("4111111111111110", String.class,
                                    new ArrayList<>()));
    }

    @Test(groups = "fast")
    public void stripNonNumericCharacters() {
        CreditCardTypeConverter c = new CreditCardTypeConverter();
        Assert.assertEquals(c.convert("4111-1111-1111-1111", String.class,
                                      new ArrayList<>()), "4111111111111111");
    }
}
