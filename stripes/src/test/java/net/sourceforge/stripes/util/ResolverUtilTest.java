package net.sourceforge.stripes.util;

import net.sourceforge.stripes.validation.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * Simple test case that tets out the basic functionality of the Resolver Util
 * class.
 *
 * @author Tim Fennell
 */
public class ResolverUtilTest {

    @Test(groups = "slow")
    public void testSimpleFind() {
        // Because the tests package depends on stripes, it's safe to assume that
        // there will be some TypeConverter subclasses in the classpath
        ResolverUtil<TypeConverter<?>> resolver = new ResolverUtil<>();
        resolver.findImplementations(TypeConverter.class, "net");
        Set<Class<? extends TypeConverter<?>>> impls = resolver.getClasses();

        // Check on a few random converters
        Assert.assertTrue(impls.contains(BooleanTypeConverter.class),
                "BooleanTypeConverter went missing.");
        Assert.assertTrue(impls.contains(DateTypeConverter.class),
                "DateTypeConverter went missing.");
        Assert.assertTrue(impls.contains(BooleanTypeConverter.class),
                "ShortTypeConverter went missing.");

        Assert.assertTrue(impls.size() >= 10,
                "Did not find all the built in TypeConverters.");
    }

    @Test(groups = "fast")
    public void testMoreSpecificFind() {
        // Because the tests package depends on stripes, it's safe to assume that
        // there will be some TypeConverter subclasses in the classpath
        ResolverUtil<TypeConverter<?>> resolver = new ResolverUtil<>();
        resolver.findImplementations(TypeConverter.class, "net.sourceforge.stripes.validation");
        Set<Class<? extends TypeConverter<?>>> impls = resolver.getClasses();

        // Check on a few random converters
        Assert.assertTrue(impls.contains(BooleanTypeConverter.class),
                "BooleanTypeConverter went missing.");
        Assert.assertTrue(impls.contains(DateTypeConverter.class),
                "DateTypeConverter went missing.");
        Assert.assertTrue(impls.contains(BooleanTypeConverter.class),
                "ShortTypeConverter went missing.");

        Assert.assertTrue(impls.size() >= 10,
                "Did not find all the built in TypeConverters.");
    }

    @Test(groups = "fast")
    public void testFindExtensionsOfClass() {
        ResolverUtil<SimpleError> resolver = new ResolverUtil<>();
        resolver.findImplementations(SimpleError.class, "net.sourceforge.stripes");

        Set<Class<? extends SimpleError>> impls = resolver.getClasses();

        Assert.assertTrue(impls.contains(LocalizableError.class),
                "LocalizableError should have been found.");
        Assert.assertTrue(impls.contains(ScopedLocalizableError.class),
                "ScopedLocalizableError should have been found.");
        Assert.assertTrue(impls.contains(SimpleError.class),
                "SimpleError itself should have been found.");
    }

    /**
     * Test interface used with the testFindZeroImplementatios() method.
     */
    private interface ZeroImplementations {
    }

    @Test(groups = "fast")
    public void testFindZeroImplementations() {
        ResolverUtil<ZeroImplementations> resolver = new ResolverUtil<>();
        resolver.findImplementations(ZeroImplementations.class, "net.sourceforge.stripes");

        Set<Class<? extends ZeroImplementations>> impls = resolver.getClasses();

        Assert.assertTrue(impls.size() == 1 && impls.contains(ZeroImplementations.class),
                "There should not have been any implementations besides the interface itself.");
    }
}
