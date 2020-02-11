package net.sourceforge.stripes.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for the CollectionUtil class
 *
 * @author Tim Fennell
 */
public class CollectionUtilTest {

    @Test

    public void testEmptyOnNullCollection() {
        Assertions.assertTrue(CollectionUtil.empty(null));
    }

    @Test

    public void testEmptyOnCollectionOfNulls() {
        Assertions.assertTrue(CollectionUtil.empty(new String[]{null, null, null}));
    }

    @Test

    public void testEmptyZeroLengthCollection() {
        Assertions.assertTrue(CollectionUtil.empty(new String[]{}));
    }

    @Test

    public void testEmptyOnCollectionOfEmptyStrings() {
        Assertions.assertTrue(CollectionUtil.empty(new String[]{"", null, ""}));
    }

    @Test

    public void testEmptyOnNonEmptyCollection1() {
        Assertions.assertFalse(CollectionUtil.empty(new String[]{"", null, "foo"}));
    }

    @Test

    public void testEmptyOnNonEmptyCollection2() {
        Assertions.assertFalse(CollectionUtil.empty(new String[]{"bar"}));
    }

    @Test

    public void testEmptyOnNonEmptyCollection3() {
        Assertions.assertFalse(CollectionUtil.empty(new String[]{"bar", "splat", "foo"}));
    }

    @Test

    public void testApplies() {
        Assertions.assertTrue(CollectionUtil.applies(null,
                                                     "foo"));
        Assertions.assertTrue(CollectionUtil.applies(new String[]{},
                                                     "foo"));
        Assertions.assertTrue(CollectionUtil.applies(new String[]{"bar", "foo"},
                                                     "foo"));
        Assertions.assertFalse(CollectionUtil.applies(new String[]{"bar", "f00"},
                                                      "foo"));
        Assertions.assertFalse(CollectionUtil.applies(new String[]{"!bar", "!foo"},
                                                      "foo"));
        Assertions.assertTrue(CollectionUtil.applies(new String[]{"!bar", "!f00"},
                                                     "foo"));
    }

    @Test

    public void testAsList() {
        List<Object> list = CollectionUtil.asList(new String[]{"foo", "bar"});
        Assertions.assertEquals(list.get(0),
                                "foo");
        Assertions.assertEquals(list.get(1),
                                "bar");

        list = CollectionUtil.asList(new String[]{});
        Assertions.assertEquals(list.size(),
                                0);

        list = CollectionUtil.asList(new int[]{0, 1, 2, 3});
        Assertions.assertEquals(list.get(0),
                                0);
        Assertions.assertEquals(list.get(1),
                                1);
        Assertions.assertEquals(list.get(2),
                                2);
        Assertions.assertEquals(list.get(3),
                                3);
    }
}
