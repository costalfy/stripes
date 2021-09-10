package net.sourceforge.stripes.validation;

import net.sourceforge.stripes.StripesTestFixture;
import net.sourceforge.stripes.util.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Locale;

public class DefaultTypeConverterFactoryTest {

    private static final Log log = Log.getInstance(DefaultTypeConverterFactoryTest.class);

    @SuppressWarnings("unchecked")
    @Test
    public void testCharTypeConverter() throws Exception {
        DefaultTypeConverterFactory factory = new DefaultTypeConverterFactory();
        factory.init(StripesTestFixture.getDefaultConfiguration());

        TypeConverter typeConverter = factory.getTypeConverter(Character.class, Locale.getDefault());
        Assertions.assertEquals(CharacterTypeConverter.class,
                                typeConverter.getClass());

        typeConverter = factory.getTypeConverter(Character.TYPE,
                                                 Locale.getDefault());
        Assertions.assertEquals(CharacterTypeConverter.class,
                                typeConverter.getClass());
    }

    /*
     * Some tests to make sure we're getting the right type converters.
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Ann {
    }

    public interface A {
    }

    public static class B implements A {
    }

    public static class C extends B {
    }

    public static class D extends C {
    }

    @Ann
    public static class E extends D {
    }

    @Ann
    public static class F {
    }

    public static abstract class BaseTC<T> implements TypeConverter<T> {

        public T convert(String input, Class<? extends T> targetType, Collection<ValidationError> errors) {
            return null;
        }

        public void setLocale(Locale locale) {
        }
    }

    public static class ATC extends BaseTC<A> {
    }

    public static class DTC extends BaseTC<D> {
    }

    public static class AnnTC extends BaseTC<Ann> {
    }

    protected void checkTypeConverter(TypeConverterFactory factory, Class<?> targetType,
            Class<?> expect) throws Exception {
        log.debug("Checking type converter for ", targetType.getSimpleName(), " is ",
                expect == null ? "null" : ATC.class.getSimpleName());
        TypeConverter<?> tc = factory.getTypeConverter(targetType, null);
        if (expect != null) {
            Assertions.assertNotNull(tc);
            Assertions.assertSame(tc.getClass(),
                                  expect);
        }
    }

    @Test
    public void testTypeConverters() throws Exception {
        DefaultTypeConverterFactory factory = new DefaultTypeConverterFactory();
        factory.init(StripesTestFixture.getDefaultConfiguration());
        factory.add(A.class, ATC.class);
        factory.add(D.class, DTC.class);
        factory.add(Ann.class, AnnTC.class);

        checkTypeConverter(factory, A.class, ATC.class);
        checkTypeConverter(factory, B.class, null);
        checkTypeConverter(factory, C.class, null);
        checkTypeConverter(factory, D.class, DTC.class);
        checkTypeConverter(factory, E.class, AnnTC.class);
        checkTypeConverter(factory, F.class, AnnTC.class);
    }
}
