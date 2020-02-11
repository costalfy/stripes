package net.sourceforge.stripes.localization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Simple test cases for the LocalizationUtility.
 *
 * @author Tim Fennell
 */
public class LocalizationUtilityTest {

    @Test
    public void testBaseCase() {
        String input = "Hello";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assertions.assertEquals(output,
                                input);
    }

    @Test
    public void testSimpleCase() {
        String input = "hello";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assertions.assertEquals(output,
                                "Hello");
    }

    @Test
    public void testWithPeriod() {
        String input = "bug.name";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assertions.assertEquals(output,
                                "Bug Name");
    }

    @Test
    public void testWithStudlyCaps() {
        String input = "bugName";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assertions.assertEquals(output,
                                "Bug Name");
    }

    @Test
    public void testComplexName() {
        String input = "bug.submittedBy.firstName";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assertions.assertEquals(output,
                                "Bug Submitted By First Name");
    }

    public enum TestEnum {
        A, B, C
    }

    public static class A {

        public static class B {

            public static class C {
            }
        }
    }

    @Test
    public void testSimpleClassName() {
        String output = LocalizationUtility.getSimpleName(TestEnum.class);
        Assertions.assertEquals(output,
                                "LocalizationUtilityTest.TestEnum");

        output = LocalizationUtility.getSimpleName(A.B.C.class);
        Assertions.assertEquals(output,
                                "LocalizationUtilityTest.A.B.C");
    }
}
