package net.sourceforge.stripes.controller;

import net.sourceforge.stripes.FilterEnabledTestBase;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.test.TestActionBean;
import net.sourceforge.stripes.test.TestBean;
import net.sourceforge.stripes.test.TestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Tests out a lot of basic binding functionality in Stripes. Ensures that
 * scalars and lists and sets of various things can be properly bound. Does not
 * test validation errors etc.
 *
 * @author Tim Fennell
 */
public class BasicBindingTests extends FilterEnabledTestBase {

    /**
     * Helper method to create a roundtrip with the TestActionBean class.
     */
    protected MockRoundtrip getRoundtrip() {
        return new MockRoundtrip(getMockServletContext(),
                                 TestActionBean.class);
    }

    @Test
    public void basicBinding() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("singleString",
                          "testValue");
        trip.addParameter("singleLong",
                          "12345");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertEquals(bean.getSingleString(),
                                "testValue");
        Assertions.assertEquals(bean.getSingleLong(),
                                new Long(12345L));
    }

    @Test
    public void bindSetsOfStrings() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("setOfStrings",
                          "testValue",
                          "testValue",
                          "testValue2",
                          "testValue3");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertTrue(bean.getSetOfStrings()
                                      .contains("testValue"));
        Assertions.assertTrue(bean.getSetOfStrings()
                                      .contains("testValue2"));
        Assertions.assertTrue(bean.getSetOfStrings()
                                      .contains("testValue3"));
        Assertions.assertEquals(bean.getSetOfStrings()
                                        .size(),
                                3);
    }

    @Test
    public void bindListOfLongs() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("listOfLongs",
                          "1",
                          "2",
                          "3",
                          "456");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertTrue(bean.getListOfLongs()
                                      .contains(1L));
        Assertions.assertTrue(bean.getListOfLongs()
                                      .contains(2L));
        Assertions.assertTrue(bean.getListOfLongs()
                                      .contains(3L));
        Assertions.assertTrue(bean.getListOfLongs()
                                      .contains(456L));
        Assertions.assertEquals(bean.getListOfLongs()
                                        .size(),
                                4);
    }

    @Test
    public void bindNonGenericListOfLongs() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("nakedListOfLongs",
                          "10",
                          "20",
                          "30",
                          "4567");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertTrue(bean.getNakedListOfLongs()
                                      .contains(10L));
        Assertions.assertTrue(bean.getNakedListOfLongs()
                                      .contains(20L));
        Assertions.assertTrue(bean.getNakedListOfLongs()
                                      .contains(30L));
        Assertions.assertTrue(bean.getNakedListOfLongs()
                                      .contains(4567L));
        Assertions.assertEquals(bean.getNakedListOfLongs()
                                        .size(),
                                4);
    }

    @Test
    public void bindNestedProperties() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("testBean.intProperty",
                          "10");
        trip.addParameter("testBean.longProperty",
                          "20");
        trip.addParameter("testBean.booleanProperty",
                          "true");
        trip.addParameter("testBean.enumProperty",
                          "Third");
        trip.execute();

        TestBean bean = trip.getActionBean(TestActionBean.class)
                .getTestBean();
        Assertions.assertNotNull(bean);
        Assertions.assertEquals(bean.getIntProperty(),
                                10);
        Assertions.assertEquals(bean.getLongProperty(),
                                new Long(20));
        Assertions.assertTrue(bean.isBooleanProperty());
        Assertions.assertEquals(bean.getEnumProperty(),
                                TestEnum.Third);
    }

    @Test
    public void bindNestedSet() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("testBean.stringSet",
                          "foo",
                          "bar",
                          "splat");
        trip.execute();

        TestBean bean = trip.getActionBean(TestActionBean.class)
                .getTestBean();
        Assertions.assertNotNull(bean);
        Assertions.assertNotNull(bean.getStringSet());
        Assertions.assertEquals(bean.getStringSet()
                                        .size(),
                                3);
        Assertions.assertTrue(bean.getStringSet()
                                      .contains("foo"));
        Assertions.assertTrue(bean.getStringSet()
                                      .contains("bar"));
        Assertions.assertTrue(bean.getStringSet()
                                      .contains("splat"));
    }

    @Test
    public void bindNumericallyIndexedProperties() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("listOfBeans[0].intProperty",
                          "0");
        trip.addParameter("listOfBeans[3].intProperty",
                          "30");
        trip.addParameter("listOfBeans[2].intProperty",
                          "20");
        trip.addParameter("listOfBeans[1].intProperty",
                          "10");
        trip.addParameter("listOfBeans[4].intProperty",
                          "40");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertEquals(bean.getListOfBeans()
                                        .get(0)
                                        .getIntProperty(),
                                00);
        Assertions.assertEquals(bean.getListOfBeans()
                                        .get(1)
                                        .getIntProperty(),
                                10);
        Assertions.assertEquals(bean.getListOfBeans()
                                        .get(2)
                                        .getIntProperty(),
                                20);
        Assertions.assertEquals(bean.getListOfBeans()
                                        .get(3)
                                        .getIntProperty(),
                                30);
        Assertions.assertEquals(bean.getListOfBeans()
                                        .get(4)
                                        .getIntProperty(),
                                40);
    }

    @Test
    public void bindStringIndexedProperties() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("mapOfLongs['one']",
                          "1");
        trip.addParameter("mapOfLongs['twentyseven']",
                          "27");
        trip.addParameter("mapOfLongs['nine']",
                          "9");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertEquals(bean.getMapOfLongs()
                                        .get("one"),
                                new Long(1));
        Assertions.assertEquals(bean.getMapOfLongs()
                                        .get("twentyseven"),
                                new Long(27));
        Assertions.assertEquals(bean.getMapOfLongs()
                                        .get("nine"),
                                new Long(9));
    }

    @Test
    public void bindStringIndexedPropertiesII() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("mapOfObjects['foo']",
                          "bar");
        trip.addParameter("mapOfObjects['cat']",
                          "meow");
        trip.addParameter("mapOfObjects['dog']",
                          "woof");
        trip.addParameter("mapOfObjects['snake']",
                          "ssss");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertEquals(bean.getMapOfObjects()
                                        .get("foo"),
                                "bar");
        Assertions.assertEquals(bean.getMapOfObjects()
                                        .get("cat"),
                                "meow");
        Assertions.assertEquals(bean.getMapOfObjects()
                                        .get("dog"),
                                "woof");
        Assertions.assertEquals(bean.getMapOfObjects()
                                        .get("snake"),
                                "ssss");
    }

    @Test
    public void bindIntArray() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("intArray",
                          "100",
                          "200",
                          "30017");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertTrue(Arrays.equals(bean.getIntArray(),
                                            new int[]{100, 200, 30017}));
    }

    @Test
    public void bindNonExistentProperty() throws Exception {
        // Should get logged but otherwise ignored...not blow up
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("foobarsplatNotProperty",
                          "100");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertNotNull(bean);
    }

    @Test
    public void bindPropertyWithoutGetterMethod() throws Exception {
        // Should be able to set it just fine
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("setOnlyString",
                          "whee");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertTrue(bean.setOnlyStringIsNotNull());
    }

    @Test
    public void bindPublicPropertyWithoutMethods() throws Exception {
        // Should be able to set it just fine
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("publicLong",
                          "12345");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        Assertions.assertEquals(new Long(12345),
                                bean.publicLong);
    }

    @Test
    void attemptToBindIntoActionBeanContext() throws Exception {
        // Should be able to set it just fine
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("context.eventName",
                          "woohaa!");
        trip.addParameter(" context.eventName",
                          "woohaa!");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        ActionBeanContext context = bean.getContext();
        Assertions.assertNotEquals(context.getEventName(),
                                   "woohaa!");
    }

    @Test
    public void attemptToBindIntoActionBeanContextII() throws Exception {
        // Should be able to set it just fine
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("Context.eventName",
                          "woohaa!");
        trip.addParameter(" Context.eventName",
                          "woohaa!");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        ActionBeanContext context = bean.getContext();
        Assertions.assertNotEquals(context.getEventName(),
                                   "woohaa!");
    }

    @Test
    public void bindArrayOfEnums() throws Exception {
        // Should be able to set it just fine
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("colors",
                          "Red",
                          "Green",
                          "Blue");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        TestActionBean.Color[] colors = bean.getColors();
        Assertions.assertNotNull(colors);
        Assertions.assertEquals(colors.length,
                                3);
        Assertions.assertEquals(colors[0],
                                TestActionBean.Color.Red);
        Assertions.assertEquals(colors[1],
                                TestActionBean.Color.Green);
        Assertions.assertEquals(colors[2],
                                TestActionBean.Color.Blue);
    }

    @Test
    public void testBindingToSubclassOfDeclaredType() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("item.id",
                          "1000000");
        trip.execute();

        TestActionBean bean = trip.getActionBean(TestActionBean.class);
        TestActionBean.PropertyLess item = bean.getItem();
        Assertions.assertEquals(item.getClass(),
                                TestActionBean.Item.class);
        Assertions.assertEquals(((TestActionBean.Item) item).getId(),
                                new Long(1000000L));
    }
}
