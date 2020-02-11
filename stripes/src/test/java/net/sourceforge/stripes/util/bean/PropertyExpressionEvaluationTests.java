package net.sourceforge.stripes.util.bean;

import net.sourceforge.stripes.FilterEnabledTestBase;
import net.sourceforge.stripes.test.TestActionBean;
import net.sourceforge.stripes.test.TestBean;
import net.sourceforge.stripes.test.TestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class PropertyExpressionEvaluationTests extends FilterEnabledTestBase {

    @Test
    public void testGetBasicPropertyType() {
        PropertyExpression expr = PropertyExpression.getExpression("singleLong");
        PropertyExpressionEvaluation eval = new PropertyExpressionEvaluation(expr, new TestActionBean());
        Class<?> type = eval.getType();
        Assertions.assertEquals(type,
                                Long.class);
    }

    @Test
    public void testGetPropertyTypeWithPropertyAccess() {
        PropertyExpression expr = PropertyExpression.getExpression("publicLong");
        PropertyExpressionEvaluation eval = new PropertyExpressionEvaluation(expr, new TestActionBean());
        Class<?> type = eval.getType();
        Assertions.assertEquals(type,
                                Long.class);
    }

    @Test
    public void testGetPropertyTypeForListOfLongs() {
        PropertyExpression expr = PropertyExpression.getExpression("listOfLongs[17]");
        PropertyExpressionEvaluation eval = new PropertyExpressionEvaluation(expr, new TestActionBean());
        Class<?> type = eval.getType();
        Assertions.assertEquals(type,
                                Long.class);
    }

    @Test
    public void testGetPropertyTypeForReadThroughList() {
        PropertyExpression expr = PropertyExpression.getExpression("listOfBeans[3].enumProperty");
        PropertyExpressionEvaluation eval = new PropertyExpressionEvaluation(expr, new TestActionBean());
        Class<?> type = eval.getType();
        Assertions.assertEquals(type,
                                TestEnum.class);
    }

    @Test
    public void testGetPropertyTypeWithBackToBackMapIndexing() {
        PropertyExpression expr = PropertyExpression.getExpression("nestedMap['foo']['bar']");
        PropertyExpressionEvaluation eval = new PropertyExpressionEvaluation(expr, new TestBean());
        Class<?> type = eval.getType();
        Assertions.assertEquals(type,
                                Boolean.class);
    }

    @Test
    public void testGetPropertyTypeWithBackToBackListArrayIndexing() {
        PropertyExpression expr = PropertyExpression.getExpression("genericArray[1][0]");
        PropertyExpressionEvaluation eval = new PropertyExpressionEvaluation(expr, new TestBean());
        Class<?> type = eval.getType();
        Assertions.assertEquals(type,
                                Float.class);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetSimpleProperties() {
        TestBean root = new TestBean();
        root.setStringProperty("testValue");
        root.setIntProperty(77);
        root.setLongProperty(777L);
        root.setEnumProperty(TestEnum.Seventh);

        Assertions.assertEquals("testValue",
                                BeanUtil.getPropertyValue("stringProperty",
                                                          root));
        Assertions.assertEquals(77,
                                BeanUtil.getPropertyValue("intProperty",
                                                          root));
        Assertions.assertEquals(777L,
                                BeanUtil.getPropertyValue("longProperty",
                                                          root));
        Assertions.assertEquals(TestEnum.Seventh,
                                BeanUtil.getPropertyValue("enumProperty",
                                                          root));
    }

    @Test
    public void testGetNestedProperties() {
        TestBean root = new TestBean();
        TestBean nested = new TestBean();
        root.setNestedBean(nested);
        nested.setStringProperty("testValue");
        nested.setIntProperty(77);
        nested.setLongProperty(777L);
        nested.setEnumProperty(TestEnum.Seventh);

        Assertions.assertEquals("testValue",
                                BeanUtil.getPropertyValue("nestedBean.stringProperty",
                                                          root));
        Assertions.assertEquals(77,
                                BeanUtil.getPropertyValue("nestedBean.intProperty",
                                                          root));
        Assertions.assertEquals(777L,
                                BeanUtil.getPropertyValue("nestedBean.longProperty",
                                                          root));
        Assertions.assertEquals(TestEnum.Seventh,
                                BeanUtil.getPropertyValue("nestedBean.enumProperty",
                                                          root));
    }

    @Test
    public void testGetNullProperties() {
        TestBean root = new TestBean();

        // Test simple and nested props, leaving out primitives
        Assertions.assertNull(BeanUtil.getPropertyValue("stringProperty",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("longProperty",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("enumProperty",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("nestedBean.stringProperty",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("nestedBean.longProperty",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("nestedBean.enumProperty",
                                                        root));

        Assertions.assertNull(BeanUtil.getPropertyValue("stringArray",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("stringList[7]",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("stringList",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("stringMap['seven']",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("stringMap",
                                                        root));
    }

    @Test
    public void testSetThenGetSimpleProperties() {
        TestBean root = new TestBean();

        BeanUtil.setPropertyValue("stringProperty",
                                  root,
                                  "testValue");
        BeanUtil.setPropertyValue("intProperty",
                                  root,
                                  77);
        BeanUtil.setPropertyValue("longProperty",
                                  root,
                                  777L);
        BeanUtil.setPropertyValue("enumProperty",
                                  root,
                                  TestEnum.Seventh);

        Assertions.assertEquals("testValue",
                                root.getStringProperty());
        Assertions.assertEquals("testValue",
                                BeanUtil.getPropertyValue("stringProperty",
                                                          root));

        Assertions.assertEquals(77,
                                root.getIntProperty());
        Assertions.assertEquals(77,
                                BeanUtil.getPropertyValue("intProperty",
                                                          root));

        Assertions.assertEquals(new Long(777L),
                                root.getLongProperty());
        Assertions.assertEquals(777L,
                                BeanUtil.getPropertyValue("longProperty",
                                                          root));

        Assertions.assertEquals(TestEnum.Seventh,
                                root.getEnumProperty());
        Assertions.assertEquals(TestEnum.Seventh,
                                BeanUtil.getPropertyValue("enumProperty",
                                                          root));

    }

    @Test
    public void testSetThenGetNestedProperties() {
        TestBean root = new TestBean();

        BeanUtil.setPropertyValue("nestedBean.stringProperty",
                                  root,
                                  "testValue");
        BeanUtil.setPropertyValue("nestedBean.intProperty",
                                  root,
                                  77);
        BeanUtil.setPropertyValue("nestedBean.longProperty",
                                  root,
                                  777L);
        BeanUtil.setPropertyValue("nestedBean.enumProperty",
                                  root,
                                  TestEnum.Seventh);

        Assertions.assertEquals("testValue",
                                root.getNestedBean()
                                        .getStringProperty());
        Assertions.assertEquals("testValue",
                                BeanUtil.getPropertyValue("nestedBean.stringProperty",
                                                          root));

        Assertions.assertEquals(77,
                                root.getNestedBean()
                                        .getIntProperty());
        Assertions.assertEquals(77,
                                BeanUtil.getPropertyValue("nestedBean.intProperty",
                                                          root));

        Assertions.assertEquals(new Long(777L),
                                root.getNestedBean()
                                        .getLongProperty());
        Assertions.assertEquals(777L,
                                BeanUtil.getPropertyValue("nestedBean.longProperty",
                                                          root));

        Assertions.assertEquals(TestEnum.Seventh,
                                root.getNestedBean()
                                        .getEnumProperty());
        Assertions.assertEquals(TestEnum.Seventh,
                                BeanUtil.getPropertyValue("nestedBean.enumProperty",
                                                          root));

        Assertions.assertSame(root.getNestedBean(),
                              BeanUtil.getPropertyValue("nestedBean",
                                                        root));
    }

    @Test
    public void testListProperties() {
        TestBean root = new TestBean();

        BeanUtil.setPropertyValue("beanList[3]",
                                  root,
                                  new TestBean());
        Assertions.assertNull(BeanUtil.getPropertyValue("beanList[0]",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("beanList[1]",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("beanList[2]",
                                                        root));
        Assertions.assertNotNull(BeanUtil.getPropertyValue("beanList[3]",
                                                           root));
        Assertions.assertNull(BeanUtil.getPropertyValue("beanList[4]",
                                                        root));

        BeanUtil.setPropertyValue("beanList[3].stringProperty",
                                  root,
                                  "testValue");
        Assertions.assertEquals("testValue",
                                root.getBeanList()
                                        .get(3)
                                        .getStringProperty());

        BeanUtil.setPropertyValue("stringList[1]",
                                  root,
                                  "testValue");
        BeanUtil.setPropertyValue("stringList[5]",
                                  root,
                                  "testValue");
        BeanUtil.setPropertyValue("stringList[9]",
                                  root,
                                  "testValue");
        BeanUtil.setPropertyValue("stringList[7]",
                                  root,
                                  "testValue");
        BeanUtil.setPropertyValue("stringList[3]",
                                  root,
                                  "testValue");

        for (int i = 0; i < 10; ++i) {
            if (i % 2 == 0) {
                Assertions.assertNull(root.getStringList()
                                              .get(i),
                                      "String list index " + i + " should be null.");
            } else {
                Assertions.assertEquals("testValue",
                                        root.getStringList()
                                                .get(i),
                                        "String list index " + i + " should be 'testValue'.");
            }
        }
    }

    @Test
    public void testMapProperties() {
        TestBean root = new TestBean();

        Assertions.assertNull(BeanUtil.getPropertyValue("stringMap['foo']",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("stringMap['bar']",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("stringMap['testValue']",
                                                        root));

        Assertions.assertNull(BeanUtil.getPropertyValue("beanMap['foo']",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("beanMap['bar']",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("beanMap['testValue']",
                                                        root));

        Assertions.assertNull(BeanUtil.getPropertyValue("beanMap['foo'].longProperty",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("beanMap['bar'].stringProperty",
                                                        root));
        Assertions.assertNull(BeanUtil.getPropertyValue("beanMap['testValue'].enumProperty",
                                                        root));

        BeanUtil.setPropertyValue("stringMap['testKey']",
                                  root,
                                  "testValue");
        Assertions.assertEquals("testValue",
                                root.getStringMap()
                                        .get("testKey"));

        BeanUtil.setPropertyValue("beanMap['testKey'].enumProperty",
                                  root,
                                  TestEnum.Fifth);
        Assertions.assertNotNull(root.getBeanMap());
        Assertions.assertNotNull(root.getBeanMap()
                                         .get("testKey"));
        Assertions.assertEquals(TestEnum.Fifth,
                                root.getBeanMap()
                                        .get("testKey")
                                        .getEnumProperty());
    }

    @Test
    public void testSetNull() {
        TestBean root = new TestBean();

        // Try setting a null on a null nested property and make sure the nested
        // property doesn't get instantiated
        Assertions.assertNull(root.getNestedBean());
        BeanUtil.setPropertyToNull("nestedBean.stringProperty",
                                   root);
        Assertions.assertNull(root.getNestedBean());

        // Now set the property set the nest bean and do it again
        root.setNestedBean(new TestBean());
        BeanUtil.setPropertyToNull("nestedBean.stringProperty",
                                   root);
        Assertions.assertNotNull(root.getNestedBean());
        Assertions.assertNull(root.getNestedBean()
                                      .getStringProperty());

        // Now set the string property and null it out for real
        root.getNestedBean()
                .setStringProperty("Definitely Not Null");
        BeanUtil.setPropertyToNull("nestedBean.stringProperty",
                                   root);
        Assertions.assertNotNull(root.getNestedBean());
        Assertions.assertNull(root.getNestedBean()
                                      .getStringProperty());

        // Now use setNullValue to trim the nestedBean
        BeanUtil.setPropertyToNull("nestedBean",
                                   root);
        Assertions.assertNull(root.getNestedBean());

        // Now try some nulling out of indexed properties
        root.setStringList(new ArrayList<>());
        root.getStringList()
                .add("foo");
        root.getStringList()
                .add("bar");
        Assertions.assertNotNull(root.getStringList()
                                         .get(0));
        Assertions.assertNotNull(root.getStringList()
                                         .get(1));
        BeanUtil.setPropertyToNull("stringList[1]",
                                   root);
        Assertions.assertNotNull(root.getStringList()
                                         .get(0));
        Assertions.assertNull(root.getStringList()
                                      .get(1));
    }

    @Test
    public void testSetNullPrimitives() {
        TestBean root = new TestBean();
        root.setBooleanProperty(true);
        root.setIntProperty(77);

        Assertions.assertEquals(77,
                                root.getIntProperty());
        Assertions.assertTrue(root.isBooleanProperty());

        BeanUtil.setPropertyToNull("intProperty",
                                   root);
        BeanUtil.setPropertyToNull("booleanProperty",
                                   root);

        Assertions.assertEquals(0,
                                root.getIntProperty());
        Assertions.assertFalse(root.isBooleanProperty());
    }

    /**
     * Tests a bug whereby the Introspector/PropertyDescriptor returns
     * inaccessible methods for getKey() and getValue() on all the JDK
     * implementations of Map. A general fix has been implmented to work up the
     * chain and find an accessible method to invoke.
     */
    @Test
    public void testMapEntryPropertyDescriptorBug() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        Map.Entry<String, String> entry = map.entrySet().iterator().next();
        String value = (String) BeanUtil.getPropertyValue("value", entry);
        Assertions.assertEquals(value,
                                "value");
    }

    /**
     * Fix for a problem whereby in certain circumstances the
     * PropertyExpressionEvaluation would not fall back to looking at instance
     * information to figure out the type info.
     */
    @Test
    public void testFallBackToInstanceInfo() {
        Map<String, TestBean> map = new HashMap<>();
        map.put("foo", new TestBean());
        map.get("foo").setStringProperty("bar");
        Map.Entry<String, TestBean> entry = map.entrySet().iterator().next();
        String value = (String) BeanUtil.getPropertyValue("value.stringProperty", entry);
        Assertions.assertEquals(value,
                                "bar");
    }

    /**
     * Following classes are part of an inheritance torture test!
     */
    public static class Wombat {

        public String getName() {
            return "Wombat";
        }
    }

    public static class SubWombat extends Wombat {

        @Override
        public String getName() {
            return "SubWombat";
        }
    }

    public static class Foo<P extends Wombat> {

        private P wombat;

        public P getWombat() {
            return this.wombat;
        }

        public void setWombat(P wombat) {
            this.wombat = wombat;
        }
    }

    public static class Owner {

        private Foo<SubWombat> foo;

        public Foo<SubWombat> getFoo() {
            return foo;
        }

        public void setFoo(final Foo<SubWombat> foo) {
            this.foo = foo;
        }
    }

    @Test
    public void testGnarlyInheritanceAndGenerics() {
        Owner owner = new Owner();
        Foo<SubWombat> foo = new Foo<>();
        owner.setFoo(foo);
        foo.setWombat(new SubWombat());
        String value = (String) BeanUtil.getPropertyValue("foo.wombat.name", owner);
        Assertions.assertEquals(value,
                                "SubWombat");
    }

}
