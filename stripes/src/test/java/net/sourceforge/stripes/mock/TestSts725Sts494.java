package net.sourceforge.stripes.mock;

import net.sourceforge.stripes.StripesTestFixture;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.StripesFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSts725Sts494 {

    @Test
    public void testSts725() {
        int count = 2;
        for (int i = 0; i < count; i++) {
            Map<String, String> params = new HashMap<>();
            params.put("ActionResolver.Packages", "foo.bar");
            MockServletContext mockServletContext = StripesTestFixture.createServletContext();
            try {
                Configuration config = StripesFilter.getConfiguration();
                Assertions.assertNotNull(config,
                                         "config is null for context " + mockServletContext.getServletContextName());
            } finally {
                mockServletContext.close();
            }
        }
    }

    @Test
    public void testSts494() {
        final List<String> l = new ArrayList<>();
        MockServletContext c = StripesTestFixture.createServletContext();
        try {
            c.addListener(new ServletContextListener() {
                public void contextInitialized(final ServletContextEvent servletContextEvent) {
                    l.add("init");
                }

                public void contextDestroyed(final ServletContextEvent servletContextEvent) {
                    l.add("destroy");
                }
            });
        } finally {
            c.close();
        }
        Assertions.assertEquals(2,
                                l.size());
        Assertions.assertEquals("init",
                                l.get(0));
        Assertions.assertEquals("destroy",
                                l.get(1));
    }
}
