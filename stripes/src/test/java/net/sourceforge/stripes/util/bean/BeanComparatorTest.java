package net.sourceforge.stripes.util.bean;

import net.sourceforge.stripes.test.TestBean;
import net.sourceforge.stripes.test.TestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test cases for the BeanComparator class that sorts lists of JavaBeans based
 * on their properties.
 *
 * @author Tim Fennell
 */
public class BeanComparatorTest {

    @Test

    public void testSimplePropertySort() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("hello");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("goodbye");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("whatever");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("huh?");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("no way!");

        beans.sort(new BeanComparator("stringProperty"));
        Assertions.assertEquals(beans.get(0)
                                        .getStringProperty(),
                                "goodbye");
        Assertions.assertEquals(beans.get(1)
                                        .getStringProperty(),
                                "hello");
        Assertions.assertEquals(beans.get(2)
                                        .getStringProperty(),
                                "huh?");
        Assertions.assertEquals(beans.get(3)
                                        .getStringProperty(),
                                "no way!");
        Assertions.assertEquals(beans.get(4)
                                        .getStringProperty(),
                                "whatever");
    }

    @Test

    public void testSimpleMultiPropertySort() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setLongProperty(2L);
        beans.get(beans.size() - 1)
                .setStringProperty("hello");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setLongProperty(2L);
        beans.get(beans.size() - 1)
                .setStringProperty("goodbye");
        beans.add(new TestBean());
        beans.get(beans.size() - 1).setLongProperty(1L);
        beans.get(beans.size() - 1)
                .setStringProperty("whatever");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setLongProperty(1L);
        beans.get(beans.size() - 1)
                .setStringProperty("huh?");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setLongProperty(3L);
        beans.get(beans.size() - 1)
                .setStringProperty("no way!");

        beans.sort(new BeanComparator("longProperty",
                                      "stringProperty"));
        Assertions.assertEquals(beans.get(0)
                                        .getStringProperty(),
                                "huh?");
        Assertions.assertEquals(beans.get(1)
                                        .getStringProperty(),
                                "whatever");
        Assertions.assertEquals(beans.get(2)
                                        .getStringProperty(),
                                "goodbye");
        Assertions.assertEquals(beans.get(3)
                                        .getStringProperty(),
                                "hello");
        Assertions.assertEquals(beans.get(4)
                                        .getStringProperty(),
                                "no way!");
    }

    @Test

    public void testNullPropertySort() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("hello");
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty(null);
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("whatever");

        beans.sort(new BeanComparator("stringProperty"));
        Assertions.assertEquals(beans.get(0)
                                        .getStringProperty(),
                                "hello");
        Assertions.assertEquals(beans.get(1)
                                        .getStringProperty(),
                                "whatever");
        Assertions.assertNull(beans.get(2)
                                      .getStringProperty());
    }

    @Test

    public void testNullPropertySort2() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty(null);
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty(null);
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setStringProperty("whatever");

        beans.sort(new BeanComparator("stringProperty"));
        Assertions.assertEquals(beans.get(0)
                                        .getStringProperty(),
                                "whatever");
        Assertions.assertNull(beans.get(1)
                                      .getStringProperty());
        Assertions.assertNull(beans.get(2)
                                      .getStringProperty());
    }

    @Test

    public void testNestedPropertySort() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setNestedBean(new TestBean());
        beans.get(beans.size() - 1)
                .getNestedBean()
                .setEnumProperty(TestEnum.Fourth);
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setNestedBean(new TestBean());
        beans.get(beans.size() - 1)
                .getNestedBean()
                .setEnumProperty(TestEnum.Second);
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setNestedBean(new TestBean());
        beans.get(beans.size() - 1)
                .getNestedBean()
                .setEnumProperty(TestEnum.Ninth);
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setNestedBean(new TestBean());
        beans.get(beans.size() - 1)
                .getNestedBean()
                .setEnumProperty(TestEnum.Eight);
        beans.add(new TestBean());
        beans.get(beans.size() - 1)
                .setNestedBean(new TestBean());
        beans.get(beans.size() - 1)
                .getNestedBean()
                .setEnumProperty(TestEnum.First);

        beans.sort(new BeanComparator("nestedBean.enumProperty"));
        Assertions.assertEquals(beans.get(0)
                                        .getNestedBean()
                                        .getEnumProperty(),
                                TestEnum.First);
        Assertions.assertEquals(beans.get(1)
                                        .getNestedBean()
                                        .getEnumProperty(),
                                TestEnum.Second);
        Assertions.assertEquals(beans.get(2)
                                        .getNestedBean()
                                        .getEnumProperty(),
                                TestEnum.Fourth);
        Assertions.assertEquals(beans.get(3)
                                        .getNestedBean()
                                        .getEnumProperty(),
                                TestEnum.Eight);
        Assertions.assertEquals(beans.get(4)
                                        .getNestedBean()
                                        .getEnumProperty(),
                                TestEnum.Ninth);
    }
}
