package net.sourceforge.stripes.action;

import net.sourceforge.stripes.FilterEnabledTestBase;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class RedirectResolutionTest extends FilterEnabledTestBase {

    //helper method
    private MockHttpServletRequest buildMockServletRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("/context", "/whatever");
        request.addLocale(Locale.US);
        return request;
    }

    @Test
    public void testPermanantRedirect() throws Exception {
        RedirectResolution resolution = new RedirectResolution("http://www.stripesframework.org",
                                                               false).setPermanent(true);
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolution.execute(buildMockServletRequest(),
                           response);

        Assertions.assertEquals(response.getStatus(),
                                HttpServletResponse.SC_MOVED_PERMANENTLY);
        Assertions.assertEquals(response.getHeaderMap()
                                        .get("Location")
                                        .iterator()
                                        .next(),
                                "http://www.stripesframework.org");
    }

    @Test
    public void testTemporaryRedirect() throws Exception {
        RedirectResolution resolution = new RedirectResolution("http://www.stripesframework.org",
                                                               false);
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolution.execute(buildMockServletRequest(),
                           response);

        Assertions.assertEquals(response.getStatus(),
                                HttpServletResponse.SC_MOVED_TEMPORARILY);
        Assertions.assertEquals(response.getRedirectUrl(),
                                "http://www.stripesframework.org");
    }

    @Test
    public void testPermanantRedirectWithParameters() throws Exception {
        RedirectResolution resolution = new RedirectResolution("http://www.stripesframework.org",
                                                               false).setPermanent(true)
                .addParameter("test",
                              "test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolution.execute(buildMockServletRequest(),
                           response);

        Assertions.assertEquals(response.getStatus(),
                                HttpServletResponse.SC_MOVED_PERMANENTLY);
        Assertions.assertEquals(response.getHeaderMap()
                                        .get("Location")
                                        .iterator()
                                        .next(),
                                "http://www.stripesframework.org?test=test");
    }

    @Test
    public void testTemporaryRedirectWithParameters() throws Exception {
        RedirectResolution resolution = new RedirectResolution("http://www.stripesframework.org",
                                                               false).addParameter("test",
                                                                                   "test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolution.execute(buildMockServletRequest(),
                           response);

        Assertions.assertEquals(response.getStatus(),
                                HttpServletResponse.SC_MOVED_TEMPORARILY);
        Assertions.assertEquals(response.getRedirectUrl(),
                                "http://www.stripesframework.org?test=test");
    }

}
