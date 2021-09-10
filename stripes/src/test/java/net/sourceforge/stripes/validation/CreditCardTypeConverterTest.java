package net.sourceforge.stripes.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CreditCardTypeConverterTest {

    @Test
    public void validNumber() {
        CreditCardTypeConverter c = new CreditCardTypeConverter();
        Assertions.assertEquals(c.convert("4111111111111111",
                                          String.class,
                                          new ArrayList<>()),
                                "4111111111111111");
    }

    @Test
    public void invalidNumber() {
        CreditCardTypeConverter c = new CreditCardTypeConverter();
        Assertions.assertNull(c.convert("4111111111111110",
                                        String.class,
                                        new ArrayList<>()));
    }

    @Test
    public void stripNonNumericCharacters() {
        CreditCardTypeConverter c = new CreditCardTypeConverter();
        Assertions.assertEquals(c.convert("4111-1111-1111-1111",
                                          String.class,
                                          new ArrayList<>()),
                                "4111111111111111");
    }
}
