package net.sourceforge.stripes.mock;

import net.sourceforge.stripes.StripesTestFixture;
import net.sourceforge.stripes.action.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;

/**
 * Unit test that is designed to test how MockRoundTrip copies cookies from the
 * request to the response.
 *
 * @author Scott Archer
 */
@UrlBinding("/mock/MockRoundtripCookies.test")
public class TestMockRoundtripCookies implements ActionBean {

    private ActionBeanContext context;

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public ActionBeanContext getContext() {
        return this.context;
    }

    /**
     * A very simple add event that returns a Forward reslution.
     */
    @DefaultHandler
    public Resolution index() {
        context.getResponse().addCookie(new Cookie("testCookie", "testCookie"));
        return new ForwardResolution("/mock/success.jsp");
    }

    // /////////////////////////////////////////////////////////////////////////
    // End of ActionBean methods and beginning of test methods. Everything
    // below this line is a test!
    // /////////////////////////////////////////////////////////////////////////
    @Test
    public void testDefaultEvent() throws Exception {
        // Setup the servlet engine
        MockServletContext ctx = StripesTestFixture.createServletContext();
        try {
            MockRoundtrip trip = new MockRoundtrip(ctx, TestMockRoundtripCookies.class);

            Cookie[] cookies = new Cookie[]{new Cookie("Cookie", "1"), new Cookie("Monster", "2"),
                new Cookie("Test", "3")};
            trip.getRequest().setCookies(cookies);
            trip.execute();

            Assertions.assertEquals(trip.getResponse()
                                            .getCookies().length,
                                    4);

            for (Cookie cookie : trip.getResponse().getCookies()) {
                if ("Cookie".equals(cookie.getName())) {
                    Assertions.assertEquals(cookie.getValue(),
                                            "1");
                } else if ("Monster".equals(cookie.getName())) {
                    Assertions.assertEquals(cookie.getValue(),
                                            "2");
                } else if ("Test".equals(cookie.getName())) {
                    Assertions.assertEquals(cookie.getValue(),
                                            "3");
                } else if ("testCookie".equals(cookie.getName())) {
                    Assertions.assertEquals(cookie.getValue(),
                                            "testCookie");
                } else {
                    throw new RuntimeException("Unexected cookie found in response!");
                }
            }
        } finally {
            ctx.close();
        }
    }
}
