package net.sourceforge.stripes.controller;

import net.sourceforge.stripes.StripesTestFixture;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.test.TestBean;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Basically a mirror of GenericsBindingTests except that in this case the type
 * variable/ parameter information is pushed further up the hierarchy. So this
 * test ensures that our type inference using type variables works when the
 * information is not directly in this class, but further up the hierarchy.
 *
 * @author Tim Fennell
 */
class Class1<A, B, C, D, E> extends GenericsBindingTestsBaseClass<A, B, C, D, E> {
}

class Class2<D, E, B, A, C> extends Class1<D, E, B, A, C> {
}

class Class3<Y, W, Z, V, X> extends Class2<Y, W, Z, V, X> {
}

class Class4<Z, Y, X, W, V> extends Class3<Z, Y, X, W, V> {
}

public class GenericsBindingTests2
        extends Class4<TestBean, Double, Boolean, Long, Date>
        implements ActionBean {

    // Stuff necessary to implement ActionBean!
    private ActionBeanContext context;

    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @DefaultHandler
    public Resolution execute() {
        return new RedirectResolution("/somewhere.jsp");
    }

    ///////////////////////////////////////////////////////////////////////////
    // Test and Support Methods
    ///////////////////////////////////////////////////////////////////////////
    static MockServletContext ctx;

    @BeforeAll
    static void setupServletContext() {
        ctx = StripesTestFixture.createServletContext();
    }

    @AfterAll
    static void closeServletContext() {
        ctx.close();
    }

    /**
     * Makes a roundtrip using the current instances' type.
     */
    protected MockRoundtrip getRoundtrip() {
        return new MockRoundtrip(ctx,
                                 GenericsBindingTests2.class);
    }

    @Test
    public void testSimpleTypeVariable() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.getRequest()
                .addLocale(Locale.ENGLISH);
        trip.addParameter("number",
                          "123.4");
        trip.execute();

        GenericsBindingTests2 bean = trip.getActionBean(GenericsBindingTests2.class);
        Assertions.assertNotNull(bean.getNumber());
        Assertions.assertEquals(bean.getNumber(),
                                new Double(123.4));
    }

    @Test
    public void testGenericBean() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.getRequest()
                .addLocale(Locale.ENGLISH);
        trip.addParameter("genericBean.genericA",
                          "123.4");
        trip.addParameter("genericBean.genericB",
                          "true");
        trip.execute();

        GenericsBindingTests2 bean = trip.getActionBean(GenericsBindingTests2.class);
        Assertions.assertNotNull(bean.getGenericBean()
                                         .getGenericA());
        Assertions.assertEquals(bean.getGenericBean()
                                        .getGenericA(),
                                new Double(123.4));
        Assertions.assertNotNull(bean.getGenericBean()
                                         .getGenericB());
        Assertions.assertEquals(bean.getGenericBean()
                                        .getGenericB(),
                                Boolean.TRUE);
    }

    @Test
    public void testTypeVariableLists() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("list[0]",
                          "true");
        trip.addParameter("list[1]",
                          "false");
        trip.addParameter("list[2]",
                          "yes");
        trip.execute();

        GenericsBindingTests2 bean = trip.getActionBean(GenericsBindingTests2.class);
        Assertions.assertNotNull(bean.getList());
        Assertions.assertEquals(bean.getList()
                                        .get(0),
                                Boolean.TRUE);
        Assertions.assertEquals(bean.getList()
                                        .get(1),
                                Boolean.FALSE);
        Assertions.assertEquals(bean.getList()
                                        .get(2),
                                Boolean.TRUE);
    }

    @Test
    public void testTypeVariableMaps() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("map[10]",
                          "1/1/2010");
        trip.addParameter("map[20]",
                          "1/1/2020");
        trip.addParameter("map[30]",
                          "1/1/2030");
        trip.execute();

        GenericsBindingTests2 bean = trip.getActionBean(GenericsBindingTests2.class);
        Assertions.assertNotNull(bean.getMap());
        Assertions.assertEquals(bean.getMap()
                                        .get(10L),
                                makeDate(2010));
        Assertions.assertEquals(bean.getMap()
                                        .get(20L),
                                makeDate(2020));
        Assertions.assertEquals(bean.getMap()
                                        .get(30L),
                                makeDate(2030));
    }

    @Test
    public void testTypeVariableNestedProperties() throws Exception {
        MockRoundtrip trip = getRoundtrip();
        trip.addParameter("bean.longProperty",
                          "1234");
        trip.addParameter("bean.stringProperty",
                          "foobar");
        trip.execute();

        GenericsBindingTests2 bean = trip.getActionBean(GenericsBindingTests2.class);
        Assertions.assertNotNull(bean.getBean());
        Assertions.assertEquals(bean.getBean()
                                        .getLongProperty(),
                                new Long(1234));
        Assertions.assertEquals(bean.getBean()
                                        .getStringProperty(),
                                "foobar");
    }

    /**
     * Helper method to manufacture dates without time components. Months are 1
     * based unlike the retarded Calendar API that uses 1 based everything else
     * and 0 based months. Sigh.
     */
    private Date makeDate(int year) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, 1 - 1,
                1);
        return cal.getTime();
    }
}
