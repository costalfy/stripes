package net.sourceforge.stripes.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests for the ReflectUtil class
 *
 * @author Tim Fennell
 */
public class ReflectUtilTest {

    @Test
    public void testAccessibleMethodBaseCase() throws Exception {
        Method m = Object.class.getMethod("getClass");
        Method m2 = ReflectUtil.findAccessibleMethod(m);
        Assertions.assertSame(m,
                              m2);
    }

    @Test
    public void testAccessibleMethodWithMapEntry() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");
        Map.Entry<String, String> entry = map.entrySet().iterator().next();
        PropertyDescriptor pd = ReflectUtil.getPropertyDescriptor(entry.getClass(), "value");
        Method m = pd.getReadMethod();
        m = ReflectUtil.findAccessibleMethod(m);
        String value = (String) m.invoke(entry);
        Assertions.assertEquals(value,
                                "bar");
    }

    @Test
    public void testCovariantProperty() {
        abstract class Base {

            abstract Object getId();
        }

        class ROSub extends Base {

            protected String id;

            @Override
            public String getId() {
                return id;
            }
        }

        class RWSub extends ROSub {

            @SuppressWarnings("unused")
            public void setId(String id) {
                this.id = id;
            }
        }

        PropertyDescriptor pd = ReflectUtil.getPropertyDescriptor(ROSub.class,
                                                                  "id");
        Assertions.assertNotNull(pd.getReadMethod(),
                                 "Read method is null");
        Assertions.assertNull(pd.getWriteMethod(),
                              "Write method is not null");

        pd = ReflectUtil.getPropertyDescriptor(RWSub.class,
                                               "id");
        Assertions.assertNotNull(pd.getReadMethod(),
                                 "Read method is null");
        Assertions.assertNotNull(pd.getWriteMethod(),
                                 "Write method is null");
    }

    interface A<S, T, U> {
    }
    interface B<V extends Number, W> extends A<W, String, V> {
    }
    interface C extends B<Integer, Long> {
    }

    @Test
    public void testResolveTypeArgsOfSuperInterface() {
        class Impl implements C {
        }
        Type[] typeArgs = ReflectUtil.getActualTypeArguments(Impl.class,
                                                             A.class);
        Assertions.assertEquals(typeArgs.length,
                                3);
        Assertions.assertEquals(typeArgs[0],
                                Long.class);
        Assertions.assertEquals(typeArgs[1],
                                String.class);
        Assertions.assertEquals(typeArgs[2],
                                Integer.class);
    }

    @Test
    public void testResolveTypeArgsOfSuperclass() {
        abstract class BaseClass1<S, T, U> {
        }
        abstract class BaseClass2<V, W> extends BaseClass1<W, String, V> {
        }
        class Impl1<X> extends BaseClass2<Integer, X> {
        }
        class Impl2 extends Impl1<Long> {
        }
        class Impl3 extends Impl2 {
        }
        Type[] typeArgs = ReflectUtil.getActualTypeArguments(Impl3.class,
                                                             BaseClass1.class);
        Assertions.assertEquals(typeArgs.length,
                                3);
        Assertions.assertEquals(typeArgs[0],
                                Long.class);
        Assertions.assertEquals(typeArgs[1],
                                String.class);
        Assertions.assertEquals(typeArgs[2],
                                Integer.class);
    }
}
