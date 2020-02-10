package net.sourceforge.stripes.localization;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Simple test cases for the LocalizationUtility.
 *
 * @author Tim Fennell
 */
public class LocalizationUtilityTest {

    @Test(groups = "fast")
    public void testBaseCase() {
        String input = "Hello";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assert.assertEquals(output, input);
    }

    @Test(groups = "fast")
    public void testSimpleCase() {
        String input = "hello";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assert.assertEquals(output, "Hello");
    }

    @Test(groups = "fast")
    public void testWithPeriod() {
        String input = "bug.name";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assert.assertEquals(output, "Bug Name");
    }

    @Test(groups = "fast")
    public void testWithStudlyCaps() {
        String input = "bugName";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assert.assertEquals(output, "Bug Name");
    }

    @Test(groups = "fast")
    public void testComplexName() {
        String input = "bug.submittedBy.firstName";
        String output = LocalizationUtility.makePseudoFriendlyName(input);
        Assert.assertEquals(output, "Bug Submitted By First Name");
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

    @Test(groups = "fast")
    public void testSimpleClassName() {
        String output = LocalizationUtility.getSimpleName(TestEnum.class);
        Assert.assertEquals(output, "LocalizationUtilityTest.TestEnum");

        output = LocalizationUtility.getSimpleName(A.B.C.class);
        Assert.assertEquals(output, "LocalizationUtilityTest.A.B.C");
    }
}
