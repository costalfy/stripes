package net.sourceforge.stripes.util;

import net.sourceforge.stripes.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Simple test case that tets out the basic functionality of the Resolver Util
 * class.
 *
 * @author Tim Fennell
 */
public class ResolverUtilTest {

    @Test
    @Tag("slow")
    public void testSimpleFind() {
        // Because the tests package depends on stripes, it's safe to assume that
        // there will be some TypeConverter subclasses in the classpath
        ResolverUtil<TypeConverter<?>> resolver = new ResolverUtil<>();
        resolver.findImplementations(TypeConverter.class,
                                     "net");
        Set<Class<? extends TypeConverter<?>>> impls = resolver.getClasses();

        // Check on a few random converters
        Assertions.assertTrue(impls.contains(BooleanTypeConverter.class),
                              "BooleanTypeConverter went missing.");
        Assertions.assertTrue(impls.contains(DateTypeConverter.class),
                              "DateTypeConverter went missing.");
        Assertions.assertTrue(impls.contains(BooleanTypeConverter.class),
                              "ShortTypeConverter went missing.");

        Assertions.assertTrue(impls.size() >= 10,
                              "Did not find all the built in TypeConverters.");
    }

    @Test
    public void testMoreSpecificFind() {
        // Because the tests package depends on stripes, it's safe to assume that
        // there will be some TypeConverter subclasses in the classpath
        ResolverUtil<TypeConverter<?>> resolver = new ResolverUtil<>();
        resolver.findImplementations(TypeConverter.class, "net.sourceforge.stripes.validation");
        Set<Class<? extends TypeConverter<?>>> impls = resolver.getClasses();

        // Check on a few random converters
        Assertions.assertTrue(impls.contains(BooleanTypeConverter.class),
                              "BooleanTypeConverter went missing.");
        Assertions.assertTrue(impls.contains(DateTypeConverter.class),
                              "DateTypeConverter went missing.");
        Assertions.assertTrue(impls.contains(BooleanTypeConverter.class),
                              "ShortTypeConverter went missing.");

        Assertions.assertTrue(impls.size() >= 10,
                              "Did not find all the built in TypeConverters.");
    }

    @Test
    public void testFindExtensionsOfClass() {
        ResolverUtil<SimpleError> resolver = new ResolverUtil<>();
        resolver.findImplementations(SimpleError.class, "net.sourceforge.stripes");

        Set<Class<? extends SimpleError>> impls = resolver.getClasses();

        Assertions.assertTrue(impls.contains(LocalizableError.class),
                              "LocalizableError should have been found.");
        Assertions.assertTrue(impls.contains(ScopedLocalizableError.class),
                              "ScopedLocalizableError should have been found.");
        Assertions.assertTrue(impls.contains(SimpleError.class),
                              "SimpleError itself should have been found.");
    }

    /**
     * Test interface used with the testFindZeroImplementatios() method.
     */
    private interface ZeroImplementations {
    }

    @Test
    public void testFindZeroImplementations() {
        ResolverUtil<ZeroImplementations> resolver = new ResolverUtil<>();
        resolver.findImplementations(ZeroImplementations.class, "net.sourceforge.stripes");

        Set<Class<? extends ZeroImplementations>> impls = resolver.getClasses();

        Assertions.assertTrue(impls.size() == 1 && impls.contains(ZeroImplementations.class),
                              "There should not have been any implementations besides the interface itself.");
    }
}
