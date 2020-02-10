package net.sourceforge.stripes.validation;

import net.sourceforge.stripes.FilterEnabledTestBase;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.util.Literal;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Unit tests that ensure that the OneToManyTypeConverter works in the context
 * of the regular binding and type conversion process.
 *
 * @author Tim Fennell
 */
@UrlBinding("/test/OneToMany.action")
public class OneToManyTypeConverterTest extends FilterEnabledTestBase implements ActionBean {

    private ActionBeanContext context;
    @Validate(converter = OneToManyTypeConverter.class)
    private List<Long> numbers;
    @Validate(converter = OneToManyTypeConverter.class)
    private List<Date> dates;

    @Test(groups = "fast")
    public void testListOfLong() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.addParameter("numbers", "123 456 789");
        trip.execute();
        OneToManyTypeConverterTest bean = trip.getActionBean(getClass());
        List<Long> numbers = bean.getNumbers();
        Assert.assertEquals(numbers, Literal.list(123L,
                                                  456L,
                                                  789L));
    }

    @Test(groups = "fast")
    public void testListOfDate() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().addLocale(Locale.ENGLISH);
        trip.addParameter("dates", "12/31/2005, 1/1/2006, 6/15/2008, 7/7/2007");
        trip.execute();
        OneToManyTypeConverterTest bean = trip.getActionBean(getClass());
        List<Date> dates = bean.getDates();

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Assert.assertEquals(dates, Literal.list(format.parse("12/31/2005"),
                format.parse("1/1/2006"),
                format.parse("6/15/2008"),
                format.parse("7/7/2007")));
    }

    @Test(groups = "fast")
    public void testListOfDateWithCommasAndNoSpaces() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().addLocale(Locale.ENGLISH);
        trip.addParameter("dates", "12/31/2005,1/1/2006,6/15/2008,7/7/2007");
        trip.execute();
        OneToManyTypeConverterTest bean = trip.getActionBean(getClass());
        List<Date> dates = bean.getDates();

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Assert.assertEquals(dates, Literal.list(format.parse("12/31/2005"),
                format.parse("1/1/2006"),
                format.parse("6/15/2008"),
                format.parse("7/7/2007")));
    }

    @Test(groups = "fast")
    public void testListOfLongWithCommasAndNoSpaces() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.addParameter("numbers", "123,456,789");
        trip.execute();
        OneToManyTypeConverterTest bean = trip.getActionBean(getClass());
        List<Long> numbers = bean.getNumbers();
        Assert.assertEquals(numbers, Literal.list(123L,
                                                  456L,
                                                  789L));
    }

    @Test(groups = "fast")
    public void testListOfLongWithCommasAndSpaces() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.addParameter("numbers", "123, 456,789 999");
        trip.execute();
        OneToManyTypeConverterTest bean = trip.getActionBean(getClass());
        List<Long> numbers = bean.getNumbers();
        Assert.assertEquals(numbers, Literal.list(123L,
                                                  456L,
                                                  789L,
                                                  999L));
    }

    @Test(groups = "fast")
    public void testWithErrors() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.addParameter("numbers", "123 456 abc 789 def");
        trip.execute();

        OneToManyTypeConverterTest bean = trip.getActionBean(getClass());
        List<Long> numbers = bean.getNumbers();

        Assert.assertNull(numbers);
        Collection<ValidationError> errors = bean.getContext().getValidationErrors().get("numbers");
        Assert.assertEquals(errors.size(), 2, "There should be two errors!");
    }

    // Dummy action method
    @DefaultHandler
    public Resolution doNothing() {
        return null;
    }

    // Getter/setter methods belows
    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public List<Long> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Long> numbers) {
        this.numbers = numbers;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}
